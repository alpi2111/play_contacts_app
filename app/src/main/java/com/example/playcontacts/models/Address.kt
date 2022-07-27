package com.example.playcontacts.models

import java.io.Serializable

data class Address(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
) : Serializable