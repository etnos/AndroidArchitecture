package com.example.androidcoroutines.mainui

import com.example.androidcoroutines.ModelUtil
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun refreshUser() {
        val user = mainViewModel.refreshUser() as UIData.Success

        Assert.assertEquals(ModelUtil.user, user)
    }
}