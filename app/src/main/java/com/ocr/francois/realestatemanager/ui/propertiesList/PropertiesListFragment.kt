package com.ocr.francois.realestatemanager.ui.propertiesList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPropertiesListBinding
import com.ocr.francois.realestatemanager.injection.Injection

class PropertiesListFragment : Fragment() {

    private lateinit var binding: FragmentPropertiesListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var propertiesAdapter: PropertiesAdapter
    private val propertiesListViewModel: PropertiesListViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    companion object {
        fun newInstance() = PropertiesListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_properties_list, container, false)
        configureRecyclerView()

        propertiesListViewModel.currencyLiveData.observeForever {  }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        with(propertiesListViewModel) {
            getPropertiesWithPhotos().observe(viewLifecycleOwner, {
                propertiesAdapter.updateProperties(it)
            })
            propertyIdSelectedLiveData.observe(viewLifecycleOwner, {
                with(propertiesAdapter) {
                    updatePropertySelected(it)
                    itemSelectedPosition?.let { position ->
                        recyclerView.scrollToPosition(position)
                    }
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        propertiesAdapter = PropertiesAdapter(propertiesListViewModel)
        propertiesAdapter.setPropertyItemClickCallback(activity as PropertiesAdapter.PropertyItemClickCallback)
    }

    private fun configureRecyclerView() {
        recyclerView = binding.fragmentPropertiesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = propertiesAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL
                )
            )
        }
    }
}