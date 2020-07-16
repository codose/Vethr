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
import com.codose.vethr.models.Favourite
import com.codose.vethr.utils.Utils
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.list_item_favorite.view.*
import java.util.*
import java.util.Date.from

/*
Created by
Oshodin Osemwingie

on 6/04/2020.
*/
class FavoriteRecyclerAdapter(val context : Context, val clickListener: FavouriteClickListener, val deleteFav : FavouriteClickListener) :
    ListAdapter<Favourite, FavoriteRecyclerAdapter.MyViewHolder>(FavouriteDiffCallback()) {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(
            context: Context,
            id: Int,
            item: Favourite,
            clickListener: FavouriteClickListener,
            deleteFav: FavouriteClickListener
        ) {
            itemView.setOnClickListener { 
                clickListener.onClick(item)
            }
            itemView.favorite_text.text = item.location
            itemView.toggleButton.setOnClickListener {
                deleteFav.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }


    private fun from(parent: ViewGroup) : MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_favorite,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, position ,item,clickListener,deleteFav)
    }
}
class FavouriteClickListener(val clickListener: (Favourite : Favourite) -> Unit){
    fun onClick(Favourite: Favourite) = clickListener(Favourite)
}

class FavouriteDiffCallback : DiffUtil.ItemCallback<Favourite>(){
    override fun areItemsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
        return oldItem == newItem
    }
}

