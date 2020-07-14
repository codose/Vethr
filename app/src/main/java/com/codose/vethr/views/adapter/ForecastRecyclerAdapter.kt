package com.codose.vethr.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codose.vethr.R
import com.codose.vethr.network.response.weatherResponse.Daily
import com.codose.vethr.utils.Utils
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.list_item_weather_forecast.view.*
import java.util.*

/*
Created by
Oshodin Osemwingie

on 6/04/2020.
*/
class ForecastRecyclerAdapter(val context : Context, val clickListener: ForecastClickListener) : ListAdapter<Daily, ForecastRecyclerAdapter.MyViewHolder>(ForecastDiffCallback()) {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(
            context: Context,
            id: Int,
            item: Daily,
            clickListener: ForecastClickListener
        ) {
            itemView.forecast_date.text = Utils.longToDate(item.dt)
            itemView.forecast_temp.text = Utils.getTempString(item.temp.max)
            itemView.forecast_image.setAnimation(Utils.getWeatherDrawable(item.weather[0].main))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }
    private fun from(parent: ViewGroup) : MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_weather_forecast,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, position ,item,clickListener)
    }
}

class ForecastClickListener(val clickListener: (forecast : Daily) -> Unit){
    fun onClick(forecast: Daily) = clickListener(forecast)
}

class ForecastDiffCallback : DiffUtil.ItemCallback<Daily>(){
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }
}
