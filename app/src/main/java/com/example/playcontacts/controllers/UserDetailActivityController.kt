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

class UserDetailActivityController : Observable() {

    private var user: User? = null

    fun updateUser(user: User) {
        this.user = user
        setChanged()
        notifyObservers()
    }

    val userInfo: User?
        get() = this.user

    init {
        try {
            println("Load map an others")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}