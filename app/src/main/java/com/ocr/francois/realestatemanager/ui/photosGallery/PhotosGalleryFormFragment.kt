package com.ocr.francois.realestatemanager.ui.photosGallery

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPhotosGalleryBinding
import com.ocr.francois.realestatemanager.events.FailureEvent
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormViewModel
import com.ocr.francois.realestatemanager.utils.ImageUtil
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.IOException

class PhotosGalleryFormFragment : Fragment(),
    PhotoSourceChoiceDialogFragment.PhotoSourceChoiceListener,
    EasyPermissions.PermissionCallbacks,
    PhotosGalleryFormAdapter.PhotosGalleryFormViewHolder.DeletePhotoClickCallback,
    TextWatcher {

    private lateinit var binding: FragmentPhotosGalleryBinding

    private var newPhotoURI: Uri? = null
    private val photoSourceChoiceDialogFragment = PhotoSourceChoiceDialogFragment.newInstance(this)

    private val propertyFormViewModel: PropertyFormViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    companion object {
        private const val TAG_PHOTO_SOURCE_CHOICE_DIALOG = "photoSourceChoiceDialog"
        private const val REQUEST_GALLERY_CODE = 100
        private const val REQUEST_CAMERA_CODE = 200
        private const val REQUEST_CAMERA_PERMISSION_CODE = 300

        fun newInstance() =
            PhotosGalleryFormFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_photos_gallery, container, false)

        configurePhotosGalleryViewPager()
        configurePhotosSourceChoiceDialog()

        return binding.root
    }

    private fun configurePhotosGalleryViewPager() {

        val photosGalleryAdapter = PhotosGalleryFormAdapter(
            this,
            this
        )
        binding.fragmentPhotosGalleryViewPager.apply {
            adapter = photosGalleryAdapter
        }

        propertyFormViewModel.photosListLiveData.observe(viewLifecycleOwner, {
            photosGalleryAdapter.updateList(it)
        })

        TabLayoutMediator(
            binding.fragmentPhotosGalleryTabLayout,
            binding.fragmentPhotosGalleryViewPager
        ) { _, _ ->

        }.attach()
    }

    private fun configurePhotosSourceChoiceDialog() {

        binding.fragmentPhotosGalleryAddPhotoButton.setOnClickListener {
            photoSourceChoiceDialogFragment.show(
                requireActivity().supportFragmentManager,
                TAG_PHOTO_SOURCE_CHOICE_DIALOG
            )
        }
    }

    private fun closePhotosSourceChoiceDialog() {
        photoSourceChoiceDialogFragment.dismiss()
    }

    override fun onCameraButtonClick() {
        closePhotosSourceChoiceDialog()

        if (EasyPermissions.hasPermissions(requireContext(), android.Manifest.permission.CAMERA)) {
            openCameraForImage()
        } else {
            EasyPermissions.requestPermissions(
                this, getString(R.string.request_camera_permission),
                REQUEST_CAMERA_PERMISSION_CODE, android.Manifest.permission.CAMERA
            )
        }
    }

    override fun onGalleryButtonClick() {
        closePhotosSourceChoiceDialog()
        openGalleryForImage()
    }

    private fun openCameraForImage() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    ImageUtil.createImageFile(requireActivity())
                } catch (ex: IOException) {

                    EventBus.getDefault()
                        .post(FailureEvent(getString(R.string.creation_file_error)))
                    null
                }
                photoFile?.also {
                    newPhotoURI = FileProvider.getUriForFile(
                        requireContext(),
                        requireContext().packageName,
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, newPhotoURI)
                }
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE)
            }
        }
    }

    private fun openGalleryForImage() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
            }
        startActivityForResult(galleryIntent, REQUEST_GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == REQUEST_GALLERY_CODE) {
            data?.let {
                val photoURI = it.data!!

                val imageStream =
                    requireActivity().contentResolver.openInputStream(photoURI)
                val selectedImage = BitmapFactory.decodeStream(imageStream)

                val imageFile =
                    ImageUtil.saveBitmapToFile(
                        requireContext(),
                        selectedImage,
                        ImageUtil.createImageFile(requireActivity())
                    )
                newPhotoURI = Uri.parse(imageFile?.path)
            }
        }

        newPhotoURI?.let {
            propertyFormViewModel.addPhotoToList(
                Photo(
                    null,
                    it.toString(),
                    null
                )
            )
            newPhotoURI = null
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == REQUEST_CAMERA_PERMISSION_CODE) {
            openCameraForImage()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    override fun onDeletePhotoClick(photo: Photo) {
        propertyFormViewModel.removePhotoFromList(photo)
    }

    override fun beforeTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
        propertyFormViewModel.onRequiredFieldChange(text.length)
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(text: Editable) {
        propertyFormViewModel.onRequiredFieldChange(text.length)
    }
}