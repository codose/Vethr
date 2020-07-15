package com.codose.vethr.views.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.codose.vethr.R
import com.codose.vethr.utils.Resource
import com.codose.vethr.utils.Utils
import com.codose.vethr.utils.Utils.getTempString
import com.codose.vethr.utils.Utils.longToDate
import com.codose.vethr.views.adapter.ForecastClickListener
import com.codose.vethr.views.adapter.ForecastRecyclerAdapter
import com.codose.vethr.views.ui.base.BaseFragment
import com.codose.vethr.views.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    private val viewModel : MainViewModel by navGraphViewModels(R.id.main_nav_graph)
    private lateinit var adapter: ForecastRecyclerAdapter

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

        adapter = ForecastRecyclerAdapter(requireContext(), ForecastClickListener {

        })
        forecast_list.adapter = adapter
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.location.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }

                is Resource.Success -> {
                    val location = it.data

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
                    val data = it.data
                    date_text.text = longToDate(data.current.dt)
                    temp_text.text = getTempString(data.current.temp)
                    location_text.text = data.timezone
                    text_weather_description.text = data.current.weather[0].description
                    view_forecast_image.setAnimation(Utils.getWeatherDrawable(data.current.weather[0].main))
                    adapter.submitList(data.daily)
                }

                is Resource.Failure -> {
                    showToast(it.message)
                    hideProgress()
                }
            }
        })
    }
}