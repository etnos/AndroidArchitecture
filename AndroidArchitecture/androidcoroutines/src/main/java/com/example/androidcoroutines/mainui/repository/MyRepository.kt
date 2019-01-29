package com.example.androidcoroutines.mainui.repository

import com.example.androidcoroutines.mainui.UIData
import com.example.androidcoroutines.mainui.UserModel
import com.example.androidcoroutines.mainui.di.DiUtil

class MyRepository(private val internetService: InternetService = DiUtil.internetService) {

    suspend fun getUser(): UserModel {
        return internetService.getUser().await()
    }

    suspend fun getUser2(): UIData {
        val response = internetService.getUser2().await()
        return if (response.isSuccessful) {
            UIData.Success(response.body()!!)
            // todo could be saved to DB
        } else {
            // todo here add logic for parsing error message
            UIData.ErrorCustom(response.message())
        }
    }
}