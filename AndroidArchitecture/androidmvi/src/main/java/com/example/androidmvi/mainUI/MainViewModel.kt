package com.example.androidmvi.mainUI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvi.dependency_injection.DI
import com.example.androidmvi.repository.UserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel : ViewModel() {

    val mainData = MutableLiveData<List<UserModel>>()


    fun refreshUsers() {
        DI.getRepository().getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mainData.value = it
            }
    }
}