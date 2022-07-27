package com.example.playcontacts.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun getRetrofit(): Retrofit {
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client.connectTimeout(5, TimeUnit.SECONDS)
        client.readTimeout(5, TimeUnit.SECONDS)
        client.writeTimeout(5, TimeUnit.SECONDS)
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client.build()).build()
    }
}