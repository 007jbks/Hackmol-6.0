package com.example.pets.ngo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pets.R
import com.example.pets.navigation.Screens
import com.example.pets.onboarding.PetAppScreen
import com.example.pets.onboarding.PetButton

@Composable
fun PawControlScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE4EC)) // light pink
            .padding(start = 24.dp, end = 24.dp,top = 32.dp, bottom = 0.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "What brings you to Paw\n Control today?",
                color = Color(0xFF3366CC),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 40.dp,bottom = 27.dp)
            )

            PetAppScreen3(navController)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(R.drawable.dogy), // replace with your drawable
                contentDescription = "Dog Peeking",
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(start=15.dp)
            )
        }
    }
}

@Composable
fun PetAppScreen3(navController: NavController) {
    Box(
        modifier = Modifier


    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PetButton(
                text = "UPDATES OF ADOPTED PET",
                leftIcon = R.drawable.group_2198383,
                showLeftIcon = true,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(32.dp))

            PetButton(
                text = "ABANDONED PETS IN NEED",
                rightIcon = R.drawable.vector_10,
                showRightIcon = true,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(32.dp))

            PetButton(
                text = "ADOPTION IN PROGRESS",
                leftIcon = R.drawable.vector_11,
                rightIcon = R.drawable.group_2198383,
                showLeftIcon = true,
                showRightIcon = true,
                onClick = {navController.navigate(Screens.petrej1.route)}
            )
        }
    }
}