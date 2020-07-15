package com.codose.vethr.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codose.vethr.R
import com.codose.vethr.network.response.searchResponse.Item
import kotlinx.android.synthetic.main.list_item_search.view.*

class SearchRecyclerAdapter(val context : Context, val clickListener: SearchClickListener) :
    ListAdapter<Item, SearchRecyclerAdapter.MyViewHolder>(SearchDiffCallback()) {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(
            context: Context,
            id: Int,
            item: Item,
            clickListener: SearchClickListener
        ) {
            itemView.setOnClickListener {
                clickListener.onClick(item)
            }
            val address = item.address
            val state = if(address.state == null) "" else address.state+","
            val street = if(address.street == null) "" else address.street+","
            val city = if(address.city == null) "" else address.city
            if(address.street == null && address.city == null){
                itemView.description_text.visibility = GONE
            }
            itemView.search_text.text = "${state}${address.countryName}"
            itemView.description_text.text = "${street}${city}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }


    private fun from(parent: ViewGroup) : MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_search,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, position ,item,clickListener)
    }
}
class SearchClickListener(val clickListener: (Search : Item) -> Unit){
    fun onClick(Search: Item) = clickListener(Search)
}

class SearchDiffCallback : DiffUtil.ItemCallback<Item>(){
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
