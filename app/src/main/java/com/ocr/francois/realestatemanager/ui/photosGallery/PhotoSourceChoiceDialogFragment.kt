package com.ocr.francois.realestatemanager.ui.photosGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ocr.francois.realestatemanager.databinding.FragmentPhotoSourceChoiceDialogBinding


class PhotoSourceChoiceDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentPhotoSourceChoiceDialogBinding
    private lateinit var photoSourceChoiceListener: PhotoSourceChoiceListener

    companion object {
        fun newInstance(listener: PhotoSourceChoiceListener) =
            PhotoSourceChoiceDialogFragment().apply {
                photoSourceChoiceListener = listener
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoSourceChoiceDialogBinding.inflate(inflater, container, false).also {
            it.fragmentPhotoSourceChoiceDialogCameraButton.setOnClickListener {
                photoSourceChoiceListener.onCameraButtonClick()
            }
            it.fragmentPhotoSourceChoiceDialogGalleryButton.setOnClickListener {
                photoSourceChoiceListener.onGalleryButtonClick()
            }
        }
        return binding.root
    }

    interface PhotoSourceChoiceListener {
        fun onCameraButtonClick()
        fun onGalleryButtonClick()
    }
}