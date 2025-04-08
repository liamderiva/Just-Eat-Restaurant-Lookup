package com.example.restaurantlookup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurantlookup.network.PostcodeApi
import com.example.restaurantlookup.ui.theme.RestaurantLookupTheme
import com.example.restaurantlookup.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantLookupTheme {
                RestaurantLookupScreen(mainViewModel)
            }
        }
    }
}

@Composable
fun RestaurantLookupScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    var userInput by remember { mutableStateOf("") }
    var responseMessage by remember { mutableStateOf("") }

    // Column to arrange elements vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TextInput Field
        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Enter text") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button that triggers an API call
        Button(
            onClick = {
                viewModel.fetchRestaurants("SE220JY")
                //fetchRestaurants("SE220JY")// Simulate API call with user input (you can replace this with actual API call logic)
                responseMessage = "API response for: $userInput"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Restaurants")
        }

        // Observe the loading state to show loading indicator
        if (viewModel.loadingState.collectAsState().value) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        // Observe the error state and show an error message
        val error = viewModel.errorState.collectAsState().value
        error?.let {
            Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
        }

        // Observe the restaurant data and display it
        val restaurants = viewModel.restaurantStateFlow.collectAsState().value
        if (restaurants.isNotEmpty()) {
            restaurants.forEach {
                Text("Name: ${it.name}")
                Text("Cuisines: ${it.cuisines}")
                Text("Rating: ${it.rating}")
                Text("Address: ${it.address}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the response message
        Text(text = responseMessage)
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantPreview() {
    RestaurantLookupTheme {
        RestaurantLookupScreen(viewModel = MainViewModel())
    }
}



