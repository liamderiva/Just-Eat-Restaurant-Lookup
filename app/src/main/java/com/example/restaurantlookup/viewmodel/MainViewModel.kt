package com.example.restaurantlookup.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantlookup.model.PostcodeResponse
import com.example.restaurantlookup.model.Restaurant
import com.example.restaurantlookup.network.Api
import com.example.restaurantlookup.network.PostcodeApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

//sealed interface RestaurantUiState {
//    data class Success(val photos: String) : RestaurantUiState
//    object Error : RestaurantUiState
//    object Loading : RestaurantUiState
//}
//
//class MainViewModel : ViewModel() {
//    /** The mutable State that stores the status of the most recent request */
//    var restaurantUiState: RestaurantUiState by mutableStateOf(RestaurantUiState.Loading)
//        private set
//
//    /**
//     * Call getMarsPhotos() on init so we can display status immediately.
//     */
//    init {
//        fetchRestaurants(postcode = "")
//    }
//
//    /**
//     * Gets Mars photos information from the Mars API Retrofit service and updates the
//     * [MarsPhoto] [List] [MutableList].
//     */
//    fun fetchRestaurants(postcode: String) {
//        viewModelScope.launch {
//            restaurantUiState = RestaurantUiState.Loading
//            restaurantUiState = try {
//                val listResult = Api.retrofitService.getRestaurantsByPostcode(postcode)
//                RestaurantUiState.Success(
//                    "Success: ${listResult.restaurants} Mars photos retrieved"
//                )
//            } catch (e: IOException) {
//                RestaurantUiState.Error
//            } catch (e: HttpException) {
//                RestaurantUiState.Error
//            }
//        }
//    }
//}

class MainViewModel : ViewModel() {
    private val _restaurantStateFlow = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurantStateFlow: StateFlow<List<Restaurant>> get() = _restaurantStateFlow

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    fun fetchRestaurants(postcode: String) {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                val response: Response<List<Restaurant>> = Api.retrofitService.getRestaurantsByPostcode(postcode)
                //val response = Api.retrofitService.getRestaurantsByPostcode(postcode)

                // Log the raw JSON response before parsing (for debugging)
                Log.d("API_RESPONSE", response.toString())

                // Check if the response is successful
                if (response.isSuccessful) {
                    val restaurantList = response.body()?: emptyList()

                    // Update the StateFlow with the fetched restaurant list
                    _restaurantStateFlow.value = restaurantList
                } else {
                    _errorState.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorState.value = "Exception: ${e.message}"
                Log.e("API_ERROR", "Error fetching data: ${e.message}")
            } finally {
                _loadingState.value = false
            }
        }
    }
}