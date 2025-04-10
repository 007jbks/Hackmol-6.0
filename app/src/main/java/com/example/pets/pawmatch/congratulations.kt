package com.example.pets.pawmatch

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pets.R


@Preview
@Composable
fun AddPhotoScreen() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation) // Your Lottie JSON file
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.5f
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE6F0)) // Light pink background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Progress Bar
            LinearProgressIndicator(
                progress = 0.5f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = Color(0xFFFF8700),
                trackColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "ADD YOUR PHOTO",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF0066CC)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Photo Upload Area
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE6E8EB).copy(alpha = 0.5f))
                    .border(
                        BorderStroke(2.dp, Color.LightGray.copy(alpha = 0.4f)),
                        RoundedCornerShape(16.dp)
                    )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Continue Button
            Button(
                onClick = { /* Handle continue */ },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066CC)),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.buttonElevation(6.dp)
            ) {
                Text(
                    text = "CONTINUE",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Floating + Button
        FloatingActionButton(
            onClick = { /* Add photo */ },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (-40).dp, y = 60.dp), // Adjust position near box
            containerColor = Color(0xFF0066CC)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Photo",
                tint = Color.White
            )
        }

        // Cat Image (bottom-left corner)
        Image(
            painter = painterResource(id = R.drawable.crying_doggy), // Replace with actual image
            contentDescription = "Cat",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomStart)
                .offset(x = 16.dp, y = (-32).dp)
        )
    }
}