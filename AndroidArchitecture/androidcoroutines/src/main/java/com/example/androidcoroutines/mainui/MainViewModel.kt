package com.example.androidcoroutines.mainui

import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidcoroutines.mainui.di.DiUtil
import com.example.androidcoroutines.mainui.repository.MyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    val data = MutableLiveData<UIData>()
    // this needed for testing
    @VisibleForTesting
    lateinit var internalData: UIData
    var repository = DiUtil.repository
    var coroutineScopeIO = CoroutineScope(Dispatchers.IO)

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

    fun testButtonClick2() {
        Timber.i("testButtonClick2")

        refreshUserWithError(repository)
    }


    fun refreshUser(repository: MyRepository) {
        Timber.i("refreshUser start")
        fetchUserJob = coroutineScopeIO.launch {
            val result = repository.getUser()
//            withContext(Dispatchers.Main) {
            Timber.i("refreshUser result $result")
            updateUI(UIData.Success(result))
//            }
        }
    }

    fun refreshUserWithError(repository: MyRepository) {
        Timber.i("refreshUser start")
        fetchUserJob = coroutineScopeIO.launch {
            val result = repository.getUser2()
//            withContext(Dispatchers.Main) {
            Timber.i("refreshUser result $result")
            updateUI(result)
//            }
        }
    }

    @WorkerThread
    fun updateUI(uiData: UIData) {
        Timber.i("updateUI $uiData")
        internalData = uiData
        data.postValue(uiData)
    }


    override fun onCleared() {
        super.onCleared()

        fetchUserJob?.cancel()
    }
}