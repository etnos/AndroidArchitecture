package com.example.androidcoroutines.mainui.repository

import com.example.androidcoroutines.mainui.UserModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface InternetService {

    @GET("todos/1")
    fun getUser(): Deferred<UserModel>

    @GET("todos/1")
    fun getUser2(): Deferred<Response<UserModel>>
}