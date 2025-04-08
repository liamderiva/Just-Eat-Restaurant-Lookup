package com.example.restaurantlookup.network

import com.example.restaurantlookup.model.PostcodeResponse
import com.example.restaurantlookup.model.Restaurant
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

//val loggingInterceptor = HttpLoggingInterceptor()
//loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//val okHttpClient = OkHttpClient.Builder()
//    .addInterceptor(loggingInterceptor)
//    .build()

private const val BASE_URL = "https://uk.api.just-eat.io/" // Your API base URL
private val Serialiser = Json { ignoreUnknownKeys = true }
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Serialiser.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
//    .client(okHttpClient)
    .build()

interface PostcodeApi {
    // Replace "your-api-endpoint" with the actual URL path
    @GET("discovery/uk/restaurants/enriched/bypostcode/{postcode}")
    suspend fun getRestaurantsByPostcode(
        @Path("postcode") postcode: String
    ): Response<List<Restaurant>>
       //Response<PostcodeResponse> // Assuming the API returns a list of restaurants
}

object Api {
    val retrofitService: PostcodeApi by lazy {
        retrofit.create(PostcodeApi::class.java)
    }
}