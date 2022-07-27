package com.example.playcontacts.api

import com.example.playcontacts.models.User
import com.example.playcontacts.models.UsersResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("users")
    suspend fun getAllUsers(): Response<UsersResponseModel?>?

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<User?>?

}