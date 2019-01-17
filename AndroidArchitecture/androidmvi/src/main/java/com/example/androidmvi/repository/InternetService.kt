package com.example.androidmvi.repository

import io.reactivex.Observable
import retrofit2.http.GET

interface InternetService {

    @GET("/todos")
    fun getMicrosoftFile(): Observable<List<UserModel>>

}