package com.codose.vethr.views.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.codose.vethr.R
import com.codose.vethr.models.Favourite
import com.codose.vethr.views.adapter.FavoriteRecyclerAdapter
import com.codose.vethr.views.adapter.FavouriteClickListener
import com.codose.vethr.views.adapter.SearchRecyclerAdapter
import com.codose.vethr.views.ui.base.BaseFragment
import com.codose.vethr.views.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_dialog.*
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavouriteFragment : BaseFragment() {
    private val viewModel : MainViewModel by navGraphViewModels(R.id.main_nav_graph)
    private lateinit var adapter: FavoriteRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteClick = FavouriteClickListener {
            val bottomSheetDialogFragment = DetailBottomSheetDialogFragment.newInstance(it.lat,it.long,it.location)
            bottomSheetDialogFragment.show(requireActivity().supportFragmentManager,"BottomSheetDialog")
        }
        val deleteFavClick = FavouriteClickListener{
            viewModel.deleteFav(it)
        }
        adapter = FavoriteRecyclerAdapter(requireContext(), favoriteClick ,deleteFavClick)
        favorite_rv.adapter = adapter
        val observer = Observer<List<Favourite>> {
            adapter.submitList(it)
            if (it.isEmpty()){
                imageView.visibility = View.VISIBLE
                textView.visibility = View.VISIBLE
            }
            else{
                imageView.visibility = View.GONE
                textView.visibility = View.GONE
            }
        }
        viewModel.allFavs!!.observe(viewLifecycleOwner,observer)
    }

}