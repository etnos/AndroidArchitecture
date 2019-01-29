package com.example.androidcoroutines.mainui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainViewModel : ViewModel() {

    val data = MutableLiveData<UIData>()
    private var userId = 0
    private var id = 2
        get() {
            field += 2
            return field
        }

    fun test() {
        Timber.i("Test")

        val user = refreshUser()
        data.value = user
    }

    fun refreshUser(): UIData {
        val user = UserModel(userId++, id, "my custom title", true)

        return UIData.Success(user)
    }
}