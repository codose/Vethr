package com.codose.vethr.views.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codose.vethr.R
import com.codose.vethr.utils.Resource
import com.codose.vethr.views.ui.base.BaseFragment
import com.codose.vethr.views.viewmodels.MainViewModel

class MainFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showBottomNav()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        viewModel.getLocation(requireContext())
    }

    private fun setUpObservers() {
        viewModel.location.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }

                is Resource.Success -> {
                    val location = it.data
                    viewModel.getWeatherData(location.latitude, location.longitude)
                }

                is Resource.Failure -> {
                    showToast(it.message)
                    hideProgress()
                }
            }
        })

        viewModel.weatherData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }

                is Resource.Success -> {
                    hideProgress()
                    showToast("Data Fetched")
                }

                is Resource.Failure -> {
                    showToast(it.message)
                    hideProgress()
                }
            }
        })
    }
}