package com.example.androidcoroutines.mainui.repository

import com.example.androidcoroutines.mainui.UserModel
import com.example.androidcoroutines.mainui.di.DiUtil

class MyRepository(private val internetService: InternetService = DiUtil.internetService) {

    suspend fun getUser(): UserModel {
        return internetService.getUser().await()
    }
}