package com.example.androidmvi.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidmvi.repository.model.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)

abstract class MyDB : RoomDatabase(){

    abstract fun userDao():UserDao
}