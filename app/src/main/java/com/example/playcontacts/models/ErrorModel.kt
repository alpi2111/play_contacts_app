package com.example.playcontacts.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorModel(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("url")
    val url: String?
) : Serializable