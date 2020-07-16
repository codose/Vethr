package com.codose.vethr.views.ui

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.codose.vethr.R
import com.codose.vethr.models.Favourite
import com.codose.vethr.network.response.weatherResponse.Daily
import com.codose.vethr.utils.Resource
import com.codose.vethr.utils.Utils
import com.codose.vethr.views.adapter.ForecastClickListener
import com.codose.vethr.views.adapter.ForecastRecyclerAdapter
import com.codose.vethr.views.viewmodels.MainViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_dialog.*

class DetailBottomSheetDialogFragment() : BottomSheetDialogFragment() {
    private val viewModel : MainViewModel by navGraphViewModels(R.id.main_nav_graph)
    private lateinit var adapter: ForecastRecyclerAdapter
    private var lat: Double =0.0
    private var long: Double =0.0
    private var location: String =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheet)
    }

    companion object{
        fun newInstance(lat: Double,long : Double,location: String) : DetailBottomSheetDialogFragment{
            val args = Bundle()
            args.putDouble("lat",lat)
            args.putDouble("long",long)
            args.putString("location",location)
            val fragment = DetailBottomSheetDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }
    lateinit var favourite: Favourite

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        long =  requireArguments().getDouble("long")
        lat =  requireArguments().getDouble("lat")
        location =  requireArguments().getString("location")!!

        viewModel.getWeatherData(lat,long)

        val observer = Observer<List<Favourite>> {
            for(fav in it){
                if(fav.location.equals(location)){
                    dialog_favorite.isChecked = true
                    favourite = fav
                }
            }
        }
        viewModel.allFavs!!.observe(viewLifecycleOwner,observer)
        adapter = ForecastRecyclerAdapter(requireContext(), ForecastClickListener {

        })
        dialog_favorite.setOnClickListener {
            if(dialog_favorite.isChecked){
                insertFavourite()
            }else{
                deleteFavourite()
            }
        }
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

    private fun deleteFavourite() {
        viewModel.deleteFav(favourite)
    }

    private fun insertFavourite() {
        favourite = Favourite()
        favourite.lat = lat
        favourite.long = long
        favourite.location = location
        viewModel.insertFav(favourite)
    }

    private fun setUpObservers() {
        viewModel.location.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }

                is Resource.Success -> {

                }

                is Resource.Failure -> {
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
                    date_text.text = Utils.longToDate(data.current.dt)
                    temp_text.text = Utils.getTempString(data.current.temp)
                    location_text.text = location
                    text_weather_description.text = data.current.weather[0].description
                    view_forecast_image.setAnimation(Utils.getWeatherDrawable(data.current.weather[0].main))
                    adapter.submitList(data.daily)
                    setupChart(data.daily)
                }

                is Resource.Failure -> {
                    hideProgress()
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
            strings.add(Utils.longToDate(data.dt))
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
        weather_chart.animateY(1000, Easing.Linear)
        weather_chart.invalidate()
        weather_chart.legend.isEnabled = false
        weather_chart.description.isEnabled = false
    }
}