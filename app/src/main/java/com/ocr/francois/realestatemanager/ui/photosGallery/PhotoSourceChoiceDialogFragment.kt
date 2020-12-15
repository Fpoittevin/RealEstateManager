package com.ocr.francois.realestatemanager.ui.photosGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ocr.francois.realestatemanager.R
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
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_source_choice_dialog, container, false)
            binding.fragmentPhotoSourceChoiceDialogCameraButton.setOnClickListener {
                photoSourceChoiceListener.onCameraButtonClick()
            }
            binding.fragmentPhotoSourceChoiceDialogGalleryButton.setOnClickListener {
                photoSourceChoiceListener.onGalleryButtonClick()
            }

        return binding.root
    }

    interface PhotoSourceChoiceListener {
        fun onCameraButtonClick()
        fun onGalleryButtonClick()
    }
}