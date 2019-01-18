package com.example.androidmvi.mainUI.mvi

import com.example.androidmvi.repository.MyRepository
import com.example.androidmvi.repository.model.UserModel
import io.reactivex.Observable
import timber.log.Timber

class ProcessFetchUsers(val repository: MyRepository) {

    fun fetchUsers(): Observable<List<UserModel>> {
        Timber.i("fetchUsers")
        return repository.getUsers()
    }
}