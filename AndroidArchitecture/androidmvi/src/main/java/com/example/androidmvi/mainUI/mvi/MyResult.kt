package com.example.androidmvi.mainUI.mvi

import com.example.androidmvi.repository.model.UserModel

sealed class MyResult {
    sealed class SyncBackendResult : MyResult() {
        data class Success(val users: List<UserModel>) : SyncBackendResult()
        data class Failure(val error: Throwable) : SyncBackendResult()
    }

    // change to your need
    sealed class SyncBackendResult2 : MyResult() {
        data class Success(val users: List<UserModel>) : SyncBackendResult2()
        data class Failure(val error: Throwable) : SyncBackendResult2()
    }
}