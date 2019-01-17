package com.example.androidmvi.repository

import com.example.androidmvi.dependency_injection.DI

class MyRepository(private val internetService: InternetService = DI.getInternetService()) {

    fun getUsers(service: InternetService = internetService) = service.getMicrosoftFile()

}