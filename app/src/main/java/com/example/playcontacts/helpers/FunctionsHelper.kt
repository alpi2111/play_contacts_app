package com.example.playcontacts.helpers

import com.example.playcontacts.models.ErrorModel
import com.google.gson.Gson
import retrofit2.Response

object FunctionsHelper {

    fun errorResponseToObject(response: Response<*>): ErrorModel {
        val gson = Gson()
        return gson.fromJson(
            response.errorBody()!!.charStream(),
            ErrorModel::class.java
        )
    }
}