package com.ocr.francois.realestatemanager.ui.propertySearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ocr.francois.realestatemanager.databinding.FragmentPropertySearchBinding

class PropertySearchFragment : Fragment() {

    private lateinit var binding: FragmentPropertySearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPropertySearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PropertySearchFragment()
    }
}