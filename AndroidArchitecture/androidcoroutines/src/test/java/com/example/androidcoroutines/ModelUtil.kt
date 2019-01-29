package com.example.androidcoroutines

import com.example.androidcoroutines.mainui.UIData
import com.example.androidcoroutines.mainui.UserModel
import com.example.androidcoroutines.mainui.repository.InternetService
import com.example.androidcoroutines.mainui.repository.MyRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Response

object ModelUtil {

    val user = UserModel(5, 8, "test title", true)
    val uiDataSuccess = UIData.Success(user)
    val responseSuccess: Response<UserModel> = Response.success(ModelUtil.user)
    val deferredResponseUserModel = CompletableDeferred<Response<UserModel>>()
    val deferredUserModel = CompletableDeferred<UserModel>()
    val internetService = object : InternetService {
        override fun getUser(): Deferred<UserModel> {
            return deferredUserModel
        }

        override fun getUser2(): Deferred<Response<UserModel>> {
            return deferredResponseUserModel
        }

    }
    val repository = MyRepository(internetService)
}