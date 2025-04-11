package com.example.pets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.pets.connectingfiles.AuthorisationViewModel
import com.example.pets.connectingfiles.viewmodels.Interest
import com.example.pets.connectingfiles.viewmodels.Interest1
import com.example.pets.navigation.Navigation
import com.example.pets.petlisting.PetListingScreen
import com.example.pets.petlisting.PetListingViewModel

var token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VycnJuYWExbWUxMTF3MTExMTExMTIxMyIsImV4cCI6MTc0NDM0MDA1Mn0.Js3S5zAPWKBchtnCdmGqUY1L_6r9hBDp5WS0ljsRllw"


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: AuthorisationViewModel
    private lateinit var viewModelpet: PetListingViewModel
    private  lateinit var viewModel1: Interest1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Compose UI content
        setContent {

            Navigation()
        }
    }
}

