package com.example.androidmvi.repository.remote

import com.example.androidmvi.repository.model.UserModel
import io.reactivex.Observable
import retrofit2.http.*

interface InternetService {

    @GET("/todos")
    fun getUsers(): Observable<List<UserModel>>

    // example of another requests
//    @GET("regions")
//    fun doGetRegions(@Header("Authorization") token: String, @Query("lang") lang: String, @Query("device_id") deviceId: String): Observable<Regions>
//
//    @POST("license")
//    fun doGetLicense(@Header("Authorization") token: String, @Query("device_id") deviceId: String,
//                     @Query("r") isRoot: Boolean, @Body requestBody: FetchLicenseBody): Observable<License>
}