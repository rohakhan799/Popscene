package com.example.movieapp.repository

import com.example.movieapp.data.dao.UserDao
import com.example.movieapp.model.UserDetails
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    companion object {
        private var mUserRepository: UserRepository? = null
    }

    suspend fun getUserData(): List<UserDetails> {
        return userDao.readAllData()

    }

    suspend fun getUserId(username:String):Int{
        return userDao.getUserId(username)
    }

    suspend fun addUser(user: UserDetails) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: UserDetails) {
        userDao.updateUser(user)
    }

    suspend fun checkEmail(email: String, password: String): UserDetails? {
        return userDao.checkEmail(email, password)
    }
}