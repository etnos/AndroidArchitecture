package com.example.androidmvi.mainUI

import com.example.androidmvi.repository.model.UserModel

data class UIModel(val userModels: List<UserModel>, val error: Throwable?) {

    companion object {
        fun idle() = UIModel(userModels = emptyList(), error = null)
    }
}