package com.codose.vethr.views.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.codose.vethr.R
import com.codose.vethr.network.response.weatherResponse.Daily
import com.codose.vethr.utils.Resource
import com.codose.vethr.utils.Utils
import com.codose.vethr.utils.Utils.getTempString
import com.codose.vethr.utils.Utils.longToDate
import com.codose.vethr.views.adapter.ForecastClickListener
import com.codose.vethr.views.adapter.ForecastRecyclerAdapter
import com.codose.vethr.views.ui.base.BaseFragment
import com.codose.vethr.views.viewmodels.MainViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseFragment() {

    private val viewModel : MainViewModel by navGraphViewModels(R.id.main_nav_graph)
    private lateinit var adapter: ForecastRecyclerAdapter
    private var isRefreshing = false
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
        swipe_refresh.setOnRefreshListener {
            isRefreshing = true
            viewModel.getLocation()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this){
            requireActivity().finish()
        }
        adapter = ForecastRecyclerAdapter(requireContext(), ForecastClickListener {

        })
        //customization
        weather_chart.setTouchEnabled(true)
        weather_chart.isDragEnabled = true
        weather_chart.setScaleEnabled(false)
        weather_chart.setPinchZoom(false)
        weather_chart.setDrawGridBackground(false)
        weather_chart.extraLeftOffset = 15f
        weather_chart.extraRightOffset = 15f
        weather_chart.xAxis.setDrawGridLines(false)
        weather_chart.axisLeft.setDrawGridLines(false)
        weather_chart.axisRight.setDrawGridLines(false)

        forecast_list.adapter = adapter
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.location.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    if(!isRefreshing){
                        showProgress()
                    }
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
                    if(!isRefreshing){
                        showProgress()
                    }
                }

                is Resource.Success -> {
                    hideProgress()
                    val data = it.data
                    date_text.text = longToDate(data.current.dt)
                    temp_text.text = getTempString(data.current.temp)
                    text_weather_description.text = data.current.weather[0].description
                    view_forecast_image.setAnimation(Utils.getWeatherDrawable(data.current.weather[0].main))
                    adapter.submitList(data.daily)
                    swipe_refresh.isRefreshing = false
                    setupChart(data.daily)
                }

                is Resource.Failure -> {
                    showToast(it.message)
                    hideProgress()
                }
            }
        })

        viewModel.locationString.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    val data = it.data
                    location_text.text = data
                }

                is Resource.Failure -> {
                    showToast(it.message)
                }
            }
        })

    }

    private fun showProgress(){
        progressView.visibility = View.VISIBLE
        mainProgressBar.visibility = View.VISIBLE
    }
    private fun hideProgress(){
        progressView.visibility = View.GONE
        mainProgressBar.visibility = View.GONE
    }

    private fun setupChart(items: List<Daily>) {

        val entries = ArrayList<Entry>()
        val strings =ArrayList<String>()
        var i=1F
        for(data in items){
            strings.add(longToDate(data.dt))
            entries.add(Entry(i,data.temp.max.toFloat()))
            i+=1
        }
        val dataSets: ArrayList<ILineDataSet> = ArrayList()

        val set1 = LineDataSet(entries, "Weather Trends")
        set1.color = resources.getColor(R.color.colorAccent);
        set1.valueTextColor = resources.getColor(R.color.textColorPrimary);
        set1.valueTextSize = 10f
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        set1.cubicIntensity = 0.2f
        set1.setDrawCircles(false);

        dataSets.add(set1)



        set1.lineWidth = 0f
        set1.setDrawValues(false)
        set1.setDrawFilled(true);
        set1.fillColor = resources.getColor(R.color.colorAccent);
        //set the transparency


        //to hide right Y and top X border
        //to hide right Y and top X border
        val rightYAxis: YAxis = weather_chart.axisRight
        rightYAxis.isEnabled = false
        val leftYAxis: YAxis = weather_chart.axisLeft
        leftYAxis.isEnabled = true
        leftYAxis.textColor = resources.getColor(R.color.textColorPrimary)
        val topXAxis: XAxis = weather_chart.xAxis
        topXAxis.isEnabled = false


        val xAxis: XAxis = weather_chart.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
        xAxis.isEnabled = false
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM


//String setter in x-Axis

//String setter in x-Axis
//        weather_chart.xAxis.valueFormatter = IndexAxisValueFormatter(strings)

        val data = LineData(dataSets)
        weather_chart.data = data
        weather_chart.animateY(1000,Easing.Linear)
        weather_chart.invalidate()
        weather_chart.legend.isEnabled = false
        weather_chart.description.isEnabled = false
    }
}