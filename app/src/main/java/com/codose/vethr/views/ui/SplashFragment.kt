package com.codose.vethr.views.ui

import android.Manifest
import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.codose.vethr.R
import com.codose.vethr.utils.Utils
import com.codose.vethr.views.ui.base.BaseFragment
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_splash.*
import java.util.ArrayList


class SplashFragment : BaseFragment() {
    private lateinit var permission : Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideBottomNav()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
        addListener()
        ActivityCompat.requestPermissions(requireActivity(),
           permission ,Utils.LOCATION)
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission[0])) {
                ActivityCompat.requestPermissions(requireActivity(), permission, Utils.LOCATION)
            }
        }

    }

    private fun addListener() {
        animation_view.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                checkLocationPermission()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

    }

    private fun checkLocationPermission() {
        Permissions.check(
            requireContext() /*context*/,
            permission,
            null /*rationale*/,
            null /*options*/,
            object : PermissionHandler() {
                override fun onGranted() {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
                }

                override fun onBlocked(
                    context: Context?,
                    blockedList: ArrayList<String>?
                ): Boolean {
                    showToast("Location Permission needed")
                    return super.onBlocked(context, blockedList)
                }

                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                    showToast("Location Permission needed")
                    super.onDenied(context, deniedPermissions)
                }

                override fun onJustBlocked(
                    context: Context?,
                    justBlockedList: ArrayList<String>?,
                    deniedPermissions: ArrayList<String>?
                ) {
                    showToast("Location Permission needed")
                    super.onJustBlocked(context, justBlockedList, deniedPermissions)
                }
            })

    }
}