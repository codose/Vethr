package com.codose.vethr.views.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.codose.vethr.R
import com.codose.vethr.network.response.searchResponse.Item
import com.codose.vethr.utils.Resource
import com.codose.vethr.views.adapter.SearchClickListener
import com.codose.vethr.views.adapter.SearchRecyclerAdapter
import com.codose.vethr.views.ui.base.BaseFragment
import com.codose.vethr.views.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : BaseFragment() {
    private val viewModel : MainViewModel by navGraphViewModels(R.id.main_nav_graph)
    private lateinit var adapter: SearchRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SearchRecyclerAdapter(requireContext(), SearchClickListener { item: Item, s: String ->
            val bottomSheetDialogFragment = DetailBottomSheetDialogFragment(item.position.lat,item.position.lng,s)
            bottomSheetDialogFragment.show(requireActivity().supportFragmentManager,"BottomSheetDialog")
        })
        search_bar_rv.adapter = adapter
        search_bar_layout.setEndIconOnClickListener {
            startSearch()
        }

        setObservers()

        search_bar.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                startSearch()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun setObservers() {
        viewModel.placeData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }

                is Resource.Success -> {
                    adapter.submitList(it.data.items)
                    hideProgress()
                }

                is Resource.Failure -> {
                    showToast(it.message)
                    hideProgress()
                }
            }

        })
    }

    private fun showProgress(){
        searchProgressView.visibility = View.VISIBLE
        searchProgressBar.visibility = View.VISIBLE
    }
    private fun hideProgress(){
        searchProgressView.visibility = View.GONE
        searchProgressBar.visibility = View.GONE
    }

    private fun startSearch() {
        val inputMethodManager : InputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(search_bar.windowToken, 0)
        if(!search_bar.text.isNullOrEmpty()){
            viewModel.searchPlace(search_bar.text.toString())
        }
    }
}