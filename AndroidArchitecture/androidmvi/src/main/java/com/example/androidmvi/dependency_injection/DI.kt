package com.example.androidmvi.dependency_injection

import android.annotation.SuppressLint
import android.content.Context
import com.example.androidmvi.MyApplication
import com.example.androidmvi.repository.InternetService
import com.example.androidmvi.repository.MyRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class DI private constructor(private val context: Context = MyApplication.globalContext) {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private val instance = DI()

        fun getInternetService() = instance.internetService
        fun getRepository() = instance.repository
    }

    private val internetService = initInternetService()
    private val repository = MyRepository(internetService)

    fun initInternetService(): InternetService {
        val interceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.tag("OkHttp").i(message) })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val baseUrl = "https://jsonplaceholder.typicode.com"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(InternetService::class.java)
    }
}