package com.example.androidmvi.mainUI.mvi

sealed class MyAction{

    data class SyncBackendAction(val parameter: Int) : MyAction()
    // change to your need
    object SyncBackendAction2 : MyAction()
}