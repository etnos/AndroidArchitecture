package com.example.androidcoroutines.mainui

import com.example.androidcoroutines.ModelUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import org.junit.Assert
import org.junit.Test

class MainViewModelTest {


    @Test
    fun networkTest() {
        runBlocking(TestCoroutineContext()) {
            val mainViewModel = MainViewModel()
            mainViewModel.repository = ModelUtil.repository
            Assert.assertNull(mainViewModel.data.value)
            mainViewModel.refreshUser(repository = ModelUtil.repository)
            ModelUtil.deferredUserModel.complete(ModelUtil.user)
            delay(1000)
            Assert.assertEquals(ModelUtil.uiDataSuccess, mainViewModel.internalData)
        }
    }

    @Test
    fun networkTestWithHandlingError() {
        runBlocking(TestCoroutineContext()) {
            val mainViewModel = MainViewModel()
            mainViewModel.repository = ModelUtil.repository
            Assert.assertNull(mainViewModel.data.value)
            mainViewModel.refreshUser(repository = ModelUtil.repository)
            ModelUtil.deferredResponseUserModel.complete(ModelUtil.responseSuccess)
            delay(1000)
            Assert.assertEquals(ModelUtil.uiDataSuccess, mainViewModel.internalData)
        }
    }
}