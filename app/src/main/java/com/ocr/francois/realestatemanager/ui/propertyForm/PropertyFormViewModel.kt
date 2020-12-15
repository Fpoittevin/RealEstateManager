package com.ocr.francois.realestatemanager.ui.propertyForm

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.repositories.PhotoRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.ui.base.BaseCurrencyViewModel
import com.ocr.francois.realestatemanager.utils.ImageUtil
import com.ocr.francois.realestatemanager.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyFormViewModel(
    private val propertyRepository: PropertyRepository,
    private val currencyRepository: CurrencyRepository,
    private val photoRepository: PhotoRepository

) : BaseCurrencyViewModel(currencyRepository) {

    var propertyWithPhotosLiveData = MediatorLiveData<PropertyWithPhotos>().apply {
        value = PropertyWithPhotos(
            Property(),
            mutableListOf()
        )
    }
    private val photosList = mutableListOf<Photo>()
    val photosListLiveData = MutableLiveData<List<Photo>>().apply {
        value = photosList
    }

    private val isPhotosListValid = MediatorLiveData<Boolean>().apply {
        addSource(photosListLiveData) { photosList ->
            value = photosList.isNotEmpty()
        }
    }
    private val areRequiredFieldsCompleted = MutableLiveData(false)
    private var isAddressChanged = false

    val isFormValid = MediatorLiveData<Boolean>().apply {
        addSource(isPhotosListValid) {
            value = areRequiredFieldsCompleted.value?.let{ areRequiredFieldsCompleted ->
                areRequiredFieldsCompleted && it
            } ?: run {
                it
            }
        }
        addSource(areRequiredFieldsCompleted) {
            value = isPhotosListValid.value?.let{ isPhotosListValid ->
                isPhotosListValid && it
            } ?: run {
                it
            }
        }
    }

    fun onRequiredFieldChange() {
        areRequiredFieldsCompleted.value = checkRequiredFields()
    }

    fun onAddressFieldChange() {
        isAddressChanged = true
        onRequiredFieldChange()
    }

    fun onSaleTimeStampChange(saleTimestamp: Long?) {
        val propertyWithPhotos = propertyWithPhotosLiveData.value
        propertyWithPhotos?.property?.saleTimestamp = saleTimestamp
        propertyWithPhotosLiveData.value = propertyWithPhotos
    }

    fun getPropertyWithPhotos(id: Long) {
        propertyWithPhotosLiveData.apply {
            addSource(propertyRepository.getPropertyWithPhotos(id)) { propertyWithPhotos ->
                propertyWithPhotos.property.apply {
                    price?.let {
                        formattedPrice =
                            convertPriceInCurrentCurrency(it).toString()
                    }
                }
                photosList.addAll(propertyWithPhotos.photosList)
                photosListLiveData.value = photosList
                value = propertyWithPhotos
            }
            addSource(currencyRepository.getCurrencyLiveData()) {
                currency = it
                value?.let { propertyWithPhotos ->
                    propertyWithPhotos.property.apply {
                        price?.let { price ->
                            formattedPrice =
                                convertPriceInCurrentCurrency(price).toString()
                        }
                    }
                }
            }
        }
    }

    fun addPhotoToList(photo: Photo) {
        photosList.add(photo)
        photosListLiveData.value = photosList
    }

    fun removePhotoFromList(photo: Photo) {
        photosList.remove(photo)
        photosListLiveData.value = photosList
        viewModelScope.launch(Dispatchers.IO) {
            photoRepository.deletePhotoFile(photo)
        }
    }

    fun createPropertyWithPhotos() {
        val propertyWithPhotos = generateDataBeforeSave()?.apply {
            property.creationTimestamp = Utils.getTodayTimestamp()
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (propertyWithPhotos != null) {
                propertyRepository.insertPropertyWithPhotos(propertyWithPhotos)
            }
        }
    }

    fun updatePropertyWithPhotos() {
        val propertyWithPhotos = generateDataBeforeSave()
        viewModelScope.launch(Dispatchers.IO) {
            propertyWithPhotos?.photosList?.forEach {
                it.propertyId = propertyWithPhotosLiveData.value!!.property.id
            }
            propertyWithPhotos?.let {
                Log.e("prop with photos", propertyWithPhotos.toString())
                propertyRepository.updatePropertyWithPhotos(
                    it,
                    isAddressChanged
                )
            }
        }
    }

    private fun generateDataBeforeSave(): PropertyWithPhotos? {
        val propertyWithPhotos = propertyWithPhotosLiveData.value

        propertyWithPhotos?.apply {
            photosList.apply {
                forEach {
                    if (!photosList.contains(it)) {
                        ImageUtil.deleteFileFromUri(Uri.parse(it.uri))
                    }
                }
                clear()
                photosListLiveData.value?.let { addAll(it) }
            }
            this.property.price?.let { price ->
                price.apply {
                    Utils.convertEuroToDollar(price)
                }
            }
        }

        return propertyWithPhotos
    }

    private fun checkRequiredFields(): Boolean =
        propertyWithPhotosLiveData.value?.let {
            with(it.property) {
                !type.isNullOrEmpty() &&
                        price != null &&
                        surface != null &&
                        !addressFirst.isNullOrEmpty() &&
                        !zipCode.isNullOrEmpty() &&
                        !city.isNullOrEmpty()
            }
        } ?: run {
            false
        }
}