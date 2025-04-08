package com.example.restaurantlookup.model

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: String,
    val name: String,
    val cuisines: List<Cuisine>,
    val rating: Rating,
    val address: Address,
)

@Serializable
data class Rating(
    val rating: Double,
)

@Serializable
data class Cuisine(
    val cuisine: String,
)

@Serializable
data class Address(
    val lineOne: String,
    val city: String,
    val postCode: String,
)