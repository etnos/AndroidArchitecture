package com.example.androidcoroutines.mainui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcoroutines.mainui.di.DiUtil
import com.example.androidcoroutines.mainui.repository.MyRepository
import kotlinx.coroutines.*
import timber.log.Timber

class MainViewModel : ViewModel() {

    val data = MutableLiveData<UIData>()
    var repository = DiUtil.repository

    private var fetchUserJob: Job? = null


    // test variables
    private var userId = 0
    private var id = 2
        get() {
            field += 2
            return field
        }

    fun testButtonClick() {
        Timber.i("testButtonClick")

        refreshUser(repository)
    }

    fun refreshUser(repository: MyRepository) {
        Timber.i("refreshUser start")
        fetchUserJob = CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getUser()
            withContext(Dispatchers.Main) {
                //do something with result
                Timber.i("refreshUser result $result")
                setValue(UIData.Success(result))
            }
        }
    }

    fun setValue(uiData: UIData) {
        Timber.i("setValue $uiData")
        data.value = uiData
    }


    override fun onCleared() {
        super.onCleared()

        fetchUserJob?.cancel()
    }
}