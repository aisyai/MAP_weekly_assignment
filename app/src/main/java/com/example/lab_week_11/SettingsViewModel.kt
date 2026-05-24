package com.example.lab_week_11

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsStore: SettingsStore) : ViewModel() {
    // The text live data is used to notify the view model when the text changes
    private val _textLiveData = MutableLiveData<String>()
    val textLiveData: LiveData<String> = _textLiveData

    init {
        // launch a coroutine to get the text from the data store asynchronously
        viewModelScope.launch {
            settingsStore.text.collect {
                _textLiveData.value = it
            }
        }
    }

    // Save the text to the data store
    fun saveText(text: String) {
        // launch a coroutine to save the text to the data store asynchronously
        viewModelScope.launch {
            settingsStore.saveText(text)
        }
    }
}