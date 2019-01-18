package com.example.androidmvi.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidmvi.repository.model.UserModel
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUsers(): Single<List<UserModel>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertUser(user: UserModel)
}