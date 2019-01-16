package com.example.androidmvi.mainUI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val mainData = MutableLiveData<String>()

}