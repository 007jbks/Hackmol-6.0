package com.example.pets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.pets.connectingfiles.AuthorisationViewModel
import com.example.pets.petlisting.PetListingScreen
import com.example.pets.petlisting.PetListingViewModel

var token: String = ""

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: AuthorisationViewModel
    private lateinit var viewModelpet: PetListingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ViewModels
        viewModel = ViewModelProvider(this)[AuthorisationViewModel::class.java]
        viewModelpet = ViewModelProvider(this)[PetListingViewModel::class.java]

        // Call the signup function
        viewModel.signup("userrrnaame111w11111213", "password123", email = "ayush1134@gmail.com")

        // Compose UI content
        setContent {
            PetListingScreen(
                viewModel = viewModelpet,
                getUserToken = { token }
            )
        }
    }
}

