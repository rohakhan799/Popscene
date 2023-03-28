package com.example.movieapp.data.dao

import androidx.room.*
import com.example.movieapp.model.UserDetails

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UserDetails)

    @Update
    suspend fun updateUser(user: UserDetails)

    @Query(value = "SELECT * FROM UserDetails")
    suspend fun readAllData(): List<UserDetails>

    @Query("SELECT Id FROM UserDetails WHERE UserName = :username")
    fun getUserId(username: String): Int

    @Query(value = "SELECT * FROM UserDetails WHERE Email LIKE :email AND Password LIKE :password")
    suspend fun checkEmail(email: String, password: String): UserDetails?
}