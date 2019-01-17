package com.example.androidmvi.repository.remote

import com.example.androidmvi.repository.UserModel
import io.reactivex.Observable
import retrofit2.http.GET

interface InternetService {

    @GET("/todos")
    fun getUsers(): Observable<List<UserModel>>

}