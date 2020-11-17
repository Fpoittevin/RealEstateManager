package com.ocr.francois.realestatemanager.ui.photosGallery

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPhotosGalleryBinding
import com.ocr.francois.realestatemanager.events.FailureEvent
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.utils.ImageUtil
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.IOException

class PhotosGalleryFragment : Fragment(),
    PhotoSourceChoiceDialogFragment.PhotoSourceChoiceListener,
    EasyPermissions.PermissionCallbacks {

    private var isEditable: Boolean = true
    private lateinit var binding: FragmentPhotosGalleryBinding

    private var newPhotoURI: Uri? = null
    lateinit var photosGalleryAdapter: PhotosGalleryAdapter

    private val photoSourceChoiceDialogFragment = PhotoSourceChoiceDialogFragment.newInstance(this)

    companion object {

        private const val ARG_IS_EDITABLE = "isEditable"
        private const val TAG_PHOTO_SOURCE_CHOICE_DIALOG = "photoSourceChoiceDialog"
        private const val REQUEST_GALLERY_CODE = 100
        private const val REQUEST_CAMERA_CODE = 200
        private const val REQUEST_CAMERA_PERMISSION_CODE = 300

        fun newInstance(isEditable: Boolean) =
            PhotosGalleryFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_EDITABLE, isEditable)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isEditable = it.getBoolean(ARG_IS_EDITABLE)
            photosGalleryAdapter = PhotosGalleryAdapter(requireContext(), isEditable)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotosGalleryBinding.inflate(inflater, container, false)

        configurePhotosGalleryViewPager()
        configurePhotosSourceChoiceDialog()

        return binding.root
    }

    fun updateList(photosList: List<Photo>) {
        photosGalleryAdapter.updateList(photosList)
    }

    private fun configurePhotosGalleryViewPager() {

        binding.fragmentPhotosGalleryViewPager.apply {
            adapter = photosGalleryAdapter
        }

        TabLayoutMediator(
            binding.fragmentPhotosGalleryTabLayout,
            binding.fragmentPhotosGalleryViewPager
        ) { _, _ ->

        }.attach()
    }

    private fun configurePhotosSourceChoiceDialog() {
        if (isEditable) {
            binding.fragmentPhotosGalleryAddPhotoButton.setOnClickListener {
                photoSourceChoiceDialogFragment.show(
                    requireActivity().supportFragmentManager,
                    TAG_PHOTO_SOURCE_CHOICE_DIALOG
                )
            }
        } else {
            binding.fragmentPhotosGalleryAddPhotoButton.visibility = View.GONE
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
                    // Error occurred while creating the File$
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
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
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
            photosGalleryAdapter.addPhotoInList(Photo(null, it.toString(), null))
            newPhotoURI = null

        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == REQUEST_CAMERA_PERMISSION_CODE) {
            openCameraForImage()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    fun getPhotosList(): List<Photo> = photosGalleryAdapter.photosList
}