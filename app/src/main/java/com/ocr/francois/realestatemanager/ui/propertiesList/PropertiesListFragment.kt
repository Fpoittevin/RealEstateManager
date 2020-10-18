package com.ocr.francois.realestatemanager.ui.propertiesList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.injection.Injection
import kotlinx.android.synthetic.main.fragment_properties_list.view.*

class PropertiesListFragment : Fragment() {

    private val propertiesAdapter = PropertiesAdapter()

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
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_properties_list, container, false)

        configureRecyclerView(view)

        propertiesListViewModel.getPropertiesWithPhotos().observe(viewLifecycleOwner, {
            propertiesAdapter.updateProperties(it)
        })

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        propertiesAdapter.setPropertyItemClickCallback(context as PropertiesAdapter.PropertyItemClickCallback)
    }

    private fun configureRecyclerView(view: View) {
        val recyclerView = view.fragment_properties_list_recycler_view
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = propertiesAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.HORIZONTAL
            )
        )
    }
}