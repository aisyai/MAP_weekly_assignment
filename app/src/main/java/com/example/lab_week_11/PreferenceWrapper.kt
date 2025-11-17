package com.example.lab_week_11

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PreferenceWrapper(private val sharedPreferences: SharedPreferences) {
// The text live data is used to notify the view model when the text changes
    private val textLiveData = MutableLiveData<String>()
    init {
// Register a listener to the shared preferences
// The listener is called when the shared preferences change
        sharedPreferences.registerOnSharedPreferenceChangeListener {
                _, key ->
            when (key) {
                KEY_TEXT -> {
                // Notify the view model that the text has changed
                // The view model will then notify the activity
                    textLiveData.postValue(
                        sharedPreferences
                        .getString(KEY_TEXT, "")
                    )
                }
            }
        }
    }
    // Save the text to the shared preferences
    fun saveText(text: String) {
        sharedPreferences.edit()
            .putString(KEY_TEXT, text)
            .apply()
    }
    // Get the text from the shared preferences
    fun getText(): LiveData<String> {
        textLiveData.postValue(sharedPreferences.getString(KEY_TEXT, ""))
        return textLiveData
    }
    // The key used to store the text in the shared preferences
    companion object{
        const val KEY_TEXT = "keyText"
    }
}

