package com.codose.vethr.views.ui.base

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.codose.vethr.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    fun showToast(message : String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(message: String){
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }
    fun goBack(){
        requireActivity().onBackPressed()
    }

    fun hideBottomNav() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = GONE
        val header = activity?.findViewById<TextView>(R.id.main_header_text)
        val imgBtn = activity?.findViewById<ImageButton>(R.id.main_header_toggle)
        header?.visibility = GONE
        imgBtn?.visibility = GONE
    }

    fun setDarkStatusBar() {
        @RequiresApi(Build.VERSION_CODES.M)
        activity?.window?.statusBarColor = activity!!.getColor(R.color.colorPrimary)
        @RequiresApi(Build.VERSION_CODES.M)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    fun setLightStatusBar() {
        @RequiresApi(Build.VERSION_CODES.M)
        activity?.window?.statusBarColor = activity!!.getColor(R.color.colorPrimary)
        @RequiresApi(Build.VERSION_CODES.M)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun showBottomNav() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = VISIBLE
        val header = activity?.findViewById<TextView>(R.id.main_header_text)
        val imgBtn = activity?.findViewById<ImageButton>(R.id.main_header_toggle)
        header?.visibility = VISIBLE
        imgBtn?.visibility = VISIBLE
    }
}