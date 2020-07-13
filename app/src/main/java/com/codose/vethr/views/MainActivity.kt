package com.codose.vethr.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.codose.vethr.R
import com.codose.vethr.utils.Utils.setThemeMode
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigationView!!, navController)
        main_header_toggle.setOnClickListener {
            setAppTheme()
        }
    }

    private fun setAppTheme() {
        val isDarkMode = Prefs.getBoolean("themeMode",false)
        if(isDarkMode){
            setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
        }else{
            setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}