package com.example.navigationmenu.ui.winrate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WinrateViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Вирейт"
    }
    val text: LiveData<String> = _text
}