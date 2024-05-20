package com.example.mybaseprojectwithhilt.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybaseprojectwithhilt.R
import com.example.mybaseprojectwithhilt.adapter.CountryListAdapter
import com.example.mybaseprojectwithhilt.databinding.FragmentMainBinding
import com.example.mybaseprojectwithhilt.util.setupSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()
    private var _binding: FragmentMainBinding? = null

    private val countriesAdapter= CountryListAdapter(arrayListOf())
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.countriesList.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=countriesAdapter
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing=false
            viewModel.refresh()
        }
        observeViewModel()
    }
    fun observeViewModel(){
        view?.setupSnackbar(viewLifecycleOwner, viewModel.snackbarText, Snackbar.LENGTH_LONG)
        viewModel.items.observe(viewLifecycleOwner, Observer {countries->
            countries?.let {
                binding.countriesList.visibility=View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        })
        viewModel.dataLoading.observe(viewLifecycleOwner, Observer {isLoading->
            isLoading?.let {
                binding.loadingView.visibility=if(it)View.VISIBLE else View.GONE
                if(it){
                    binding.listError.visibility=View.GONE
                    binding.countriesList.visibility=View.GONE
                }
            }
        })
        viewModel.isDataLoadingError.observe(viewLifecycleOwner,{isDataLoadingError->
            isDataLoadingError?.let { binding.listError.visibility=if(it) View.VISIBLE else View.GONE }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}