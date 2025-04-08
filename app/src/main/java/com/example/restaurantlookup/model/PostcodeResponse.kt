package com.example.restaurantlookup.model

import kotlinx.serialization.Serializable

@Serializable
data class PostcodeResponse (
    val restaurants: List<Restaurant>
)
