package com.example.pets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.pets.connectingfiles.AuthorisationViewModel
import com.example.pets.connectingfiles.viewmodels.Interest
import com.example.pets.connectingfiles.viewmodels.Interest1
import com.example.pets.petlisting.PetListingScreen
import com.example.pets.petlisting.PetListingViewModel

var token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VycnJuYWExbWUxMTF3MTExMTExMTIxMyIsImV4cCI6MTc0NDM0MDA1Mn0.Js3S5zAPWKBchtnCdmGqUY1L_6r9hBDp5WS0ljsRllw"


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: AuthorisationViewModel
    private lateinit var viewModelpet: PetListingViewModel
    private  lateinit var viewModel1: Interest1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel1=ViewModelProvider(this)[Interest1::class.java]

        // Initialize the ViewModels
        viewModel = ViewModelProvider(this)[AuthorisationViewModel::class.java]
        viewModelpet = ViewModelProvider(this)[PetListingViewModel::class.java]


        // Call the signup function

        viewModel1.searchPet(
            species = "Dog",
            breed = "Labrador",
            gender = "Male",
            weight_range = "1-10",
            age_range = "1-3",
            color_markings = "Golden"
        )
        // Compose UI content
        setContent {
            PetListingScreen(
                viewModel = viewModelpet,
                getUserToken = { token }
            )
        }
    }
}

