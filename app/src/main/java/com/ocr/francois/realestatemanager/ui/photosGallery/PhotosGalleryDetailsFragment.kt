package com.ocr.francois.realestatemanager.ui.photosGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPhotosGalleryDetailsBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsViewModel

class PhotosGalleryDetailsFragment : Fragment() {

    companion object {

        fun newInstance() =
            PhotosGalleryDetailsFragment()
    }

    private lateinit var binding: FragmentPhotosGalleryDetailsBinding

    private val propertyDetailsViewModel: PropertyDetailsViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_photos_gallery_details,
            container,
            false
        )
        binding.apply {
            lifecycleOwner = this@PhotosGalleryDetailsFragment
            viewModel = propertyDetailsViewModel
        }

        configurePhotosGalleryViewPager()

        return binding.root
    }

    private fun configurePhotosGalleryViewPager() {

        val photosGalleryAdapter = PhotosGalleryAdapter()
        binding.fragmentPhotosGalleryDetailsViewPager.apply {
            adapter = photosGalleryAdapter
        }

        propertyDetailsViewModel.propertyWithPhotos.observe(viewLifecycleOwner, {
            photosGalleryAdapter.updateList(
                it.photosList
            )
        })

        TabLayoutMediator(
            binding.fragmentPhotosGalleryDetailsTabLayout,
            binding.fragmentPhotosGalleryDetailsViewPager
        ) { _, _ ->

        }.attach()
    }
}