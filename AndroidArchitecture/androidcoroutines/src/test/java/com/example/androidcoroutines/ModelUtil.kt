package com.example.androidcoroutines

import com.example.androidcoroutines.mainui.UIData
import com.example.androidcoroutines.mainui.UserModel

object ModelUtil {

    val user = UserModel(5, 8, "test title", true)
    val uiDataSuccess = UIData.Success(user)
}