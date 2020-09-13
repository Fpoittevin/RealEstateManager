package com.ocr.francois.realestatemanager.ui.propertyCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPropertyCreationBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.ui.photosGallery.PhotosGalleryFragment
import com.ocr.francois.realestatemanager.viewmodels.PropertyCreationViewModel
import java.util.*

class PropertyCreationFragment : Fragment() {

    private lateinit var binding: FragmentPropertyCreationBinding

    private val propertyCreationViewModel: PropertyCreationViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = PropertyCreationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPropertyCreationBinding.inflate(inflater, container, false)
            .also {
                it.saveButton.setOnClickListener { saveProperty() }
            }
        configurePhotosGallery()
        //configurePhotosGalleryViewPager()
        //configurePictureSourceChoiceDialog()

        return binding.root
    }

    private fun configurePhotosGallery() {
        val photosGalleryFragment = PhotosGalleryFragment.newInstance(true)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_property_creation_gallery_container, photosGalleryFragment)
            .commit()
    }

    private fun saveProperty() {

        val property = Property(
            null,
            binding.fragmentPropertyCreationTypeTextInput.text.toString(),
            binding.fragmentPropertyCreationPriceTextInput.text.toString().toIntOrNull(),
            binding.fragmentPropertyCreationSurfaceTextInput.text.toString().toFloatOrNull(),
            binding.fragmentPropertyCreationNumberOfRoomsTextInput.text.toString().toIntOrNull(),
            binding.fragmentPropertyCreationNumberOfBathroomsTextInput.text.toString()
                .toIntOrNull(),
            binding.fragmentPropertyCreationNumberOfBedroomsTextInput.text.toString().toIntOrNull(),
            binding.fragmentPropertyCreationDescriptionTextInput.text.toString(),
            binding.fragmentPropertyCreationAddressTextInput.text.toString(),
            binding.fragmentPropertyCreationAddressComplementsTextInput.text.toString(),
            binding.fragmentPropertyCreationDistrictTextInput.text.toString(),
            binding.fragmentPropertyCreationCityTextInput.text.toString(),
            binding.fragmentPropertyCreationZipCodeTextInput.text.toString(),
            binding.fragmentPropertyCreationStateTextInput.text.toString(),
            binding.fragmentPropertyCreationPointsOfInterestTextInput.text.toString(),
            Date().time,
            null,
            binding.fragmentPropertyCreationEstateAgentTextInput.text.toString(),
            null,
            null
        )

        propertyCreationViewModel.saveProperty(property)
        activity?.finish()
    }
}

/*
private fun configurePhotosGalleryViewPager() {
    val photosGalleryAdapter = PhotosGalleryAdapter()
    binding.fragmentPropertyCreationGalleryViewPager.apply {
        adapter = photosGalleryAdapter
    }
    propertyCreationViewModel.photosURIListLiveData.observe(viewLifecycleOwner, {
        photosGalleryAdapter.updateList(it)
    })
}

private fun configurePictureSourceChoiceDialog() {
    binding.fragmentPropertyCreationAddPictureButton.setOnClickListener {
        pictureSourceChoiceDialogFragment.show(
            requireActivity().supportFragmentManager,
            "pictureSourceChoiceDialog"
        )
    }
}

private fun closePictureSourceChoiceDialog() {
    pictureSourceChoiceDialogFragment.dismiss()
}

override fun onCameraButtonClick() {
    closePictureSourceChoiceDialog()

    if (EasyPermissions.hasPermissions(requireContext(), android.Manifest.permission.CAMERA)) {
        openCameraForImage()
    } else {
        EasyPermissions.requestPermissions(
            this, "we need camera",
            REQUEST_CAMERA_PERMISSION_CODE, android.Manifest.permission.CAMERA
        )
    }
}

override fun onGalleryButtonClick() {
    closePictureSourceChoiceDialog()
    openGalleryForImage()
}

private fun openCameraForImage() {
    var photoURI: Uri
    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
        takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
            val photoFile: File? = try {
                propertyCreationViewModel.createImageFile(requireActivity())
            } catch (ex: IOException) {
                // Error occurred while creating the File$
                null
            }
            photoFile?.also {
                photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    "com.ocr.francois.realestatemanager",
                    it
                )
                propertyCreationViewModel.addImageURIInList(photoURI)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            }
            startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE)
        }
    }
}

private fun openGalleryForImage() {
    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    galleryIntent.type = "image/*"
    startActivityForResult(galleryIntent, REQUEST_GALLERY_CODE)
}

override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    if (requestCode == REQUEST_CAMERA_PERMISSION_CODE) {
        openCameraForImage()
    }
}

override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (resultCode == AppCompatActivity.RESULT_OK) {
        when (requestCode) {
            REQUEST_CAMERA_CODE -> {

            }
            REQUEST_GALLERY_CODE -> {
                data?.let {
                    val photoURI = it.data!!

                    val imageStream =
                        requireActivity().contentResolver.openInputStream(photoURI)
                    val selectedImage = BitmapFactory.decodeStream(imageStream)

                    val imageFile =
                        ImageUtil.saveBitmapToFile(
                            requireContext(),
                            selectedImage,
                            propertyCreationViewModel.createImageFile(requireActivity())
                        )
                    propertyCreationViewModel.addImageURIInList(Uri.parse(imageFile?.path))
                }
            }
        }
    }
*/