package com.example.movieapp.viewModel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.UserDetails
import com.example.movieapp.model.UserManager
import com.example.movieapp.repository.BookmarkRepository
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.repository.UserRepository
import com.example.movieapp.ui.activity.HomeActivity
import com.example.movieapp.ui.activity.Welcome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    var userData: List<UserDetails>
    var mutableUserData = MutableLiveData<List<UserDetails>>()
    var userLiveData: LiveData<List<UserDetails>> = mutableUserData

    var user: UserDetails? = null
    var mutableUser = MutableLiveData<UserDetails>()
    var userObjLive: LiveData<UserDetails> = mutableUser

    @Inject
    lateinit var userRepo: UserRepository

    @Inject
    lateinit var bookmarkRepository: BookmarkRepository

    @Inject
    lateinit var moviesRepository: MoviesRepository

    init {
        userData = emptyList()
    }

    suspend fun init() {
        viewModelScope.launch {
            userData = userRepo.getUserData()
            mutableUserData.postValue(userData)
        }
    }

    suspend fun addUser(user: UserDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.addUser(user)
            userData = userRepo.getUserData()
            mutableUserData.postValue(userData)
        }
    }

    suspend fun updateUser(user: UserDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.updateUser(user)
        }
    }

    suspend fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            userData = userRepo.getUserData()
            mutableUserData.postValue(userData)
        }
    }

    fun isLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            user = userRepo.checkEmail(email, password)
            if (user != null) {
                UserManager.userObj = user
            }
            mutableUser.postValue(user)
        }
    }

    suspend fun getUserId(username: String): Int {
        var userID: Int = 0
        viewModelScope.launch(Dispatchers.IO) {
            userID = userRepo.getUserId(username)
        }
        return userID
    }

    suspend fun loadBookmarks() {
        withContext(Dispatchers.IO) {
            UserManager.userObj?.let { bookmarkRepository.readBookmarkID(it.userId) }
        }
    }

    suspend fun loadGenres() {
        moviesRepository.loadGenres()
    }

    fun getSharedPreference(context: Context,sharedPreferences: SharedPreferences) {
        var intent: Intent? = null
        if (sharedPreferences?.getBoolean("firstrun", true) == true) {
            sharedPreferences?.edit()?.putBoolean("firstrun", false)?.apply()
            intent = Intent(context, Welcome::class.java)
        } else {
            intent = Intent(context, HomeActivity::class.java)
        }
        startActivity(context, intent, null)
    }
}