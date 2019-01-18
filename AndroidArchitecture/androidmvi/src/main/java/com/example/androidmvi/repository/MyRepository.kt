package com.example.androidmvi.repository

import com.example.androidmvi.dependency_injection.DI
import com.example.androidmvi.repository.local.UserDao
import com.example.androidmvi.repository.model.UserModel
import com.example.androidmvi.repository.remote.InternetService
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MyRepository(
    private val internetService: InternetService = DI.getInternetService(),
    private val userDao: UserDao = DI.getUserDao()
) {

    fun getUsers(): Observable<List<UserModel>> {
        val remote = getUsersRemote()
        val local = getUsersFromDb()
        return Observable.concatArrayEager(remote, local)
            .debounce(250, TimeUnit.MILLISECONDS)
    }

    private fun getUsersRemote() = internetService.getUsers()
        .doOnNext { users -> users.forEach { userDao.insertUser(it) } }

    private fun getUsersFromDb() = userDao.getUsers().toObservable()
}