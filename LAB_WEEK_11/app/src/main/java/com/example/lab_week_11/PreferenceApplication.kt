package com.example.lab_week_11

import android.app.Application
import android.content.Context

class PreferenceApplication : Application() {
    lateinit var preferenceWrapper: PreferenceWrapper
    override fun onCreate() {
        super.onCreate()

        // Initialize the preference wrapper
        // The preference wrapper is used to access the shared preferences
        preferenceWrapper = PreferenceWrapper(
            // Get the shared preferences
            // The shared preferences are stored in the file
            // /data/data/com.example.lab_week_11_a/shared_prefs/prefs.xml
            getSharedPreferences(
                // The name of the file
                "prefs",
                // The mode of the file
                // MODE_PRIVATE means that only this application can access the file
                Context.MODE_PRIVATE
            )
        )
    }
}
