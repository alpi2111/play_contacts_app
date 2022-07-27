package com.example.playcontacts.controllers

import com.example.playcontacts.api.ApiConfig
import com.example.playcontacts.api.ApiInterface
import com.example.playcontacts.models.User
import com.example.playcontacts.models.UsersResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Observable

class MainActivityController : Observable() {

    private var user: User? = null
    private var usersResponseModel: UsersResponseModel? = null

    fun updateUser(user: User) {
        this.user = user
        setChanged()
        notifyObservers()
    }

    private fun updateAllUsers(users: UsersResponseModel) {
        this.usersResponseModel = users
        setChanged()
        notifyObservers()
    }

    val userInfo: User?
        get() = this.user

    val allUsers: UsersResponseModel?
        get() = this.usersResponseModel

    init {
        try {
            val service = ApiConfig.getRetrofit().create(ApiInterface::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getAllUsers() ?: return@launch
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        updateAllUsers(response.body()!!)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}