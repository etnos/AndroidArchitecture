package com.example.androidcoroutines.mainui

sealed class UIData {
    data class Success(val user: UserModel) : UIData()
    data class Error(val error: Throwable) : UIData()
    data class ErrorCustom(val customError: String) : UIData()
}