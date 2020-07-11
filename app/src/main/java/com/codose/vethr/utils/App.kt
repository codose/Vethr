package com.codose.vethr.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.os.Build
import com.pixplicity.easyprefs.library.Prefs


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initPrefs()
    }

    private fun initPrefs() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Earnbox Channel 1", NotificationManager.IMPORTANCE_HIGH)
            channel.shouldVibrate()
            channel.description = "Notify users on important updates with vibration and sound"
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "com.wizchat.earnbox_NOTIFICATION_CHANNEL_ID"
        fun checkNetwork(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    }
}
