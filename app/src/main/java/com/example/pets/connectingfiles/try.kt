package com.example.pets.connectingfiles


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pets.R
import com.example.pets.navigation.Screens


@Composable
fun SignupScreenngo(viewModel: AuthorisationViewModel, navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val authState = viewModel.authState.collectAsState().value

    LaunchedEffect(authState) {
        if (authState is AuthorisationViewModel.AuthState.Success) {
            navController.navigate(Screens.whyint.route) {
                popUpTo(Screens.pawcontrol.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE6F0))
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.dog),
            contentDescription = "dog",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Paw Control",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3366CC)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Meet. Match. Master pet parenthood",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9900)
        )

        Spacer(modifier = Modifier.height(26.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Enter Email") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.85f),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Registration Number") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.85f),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Create  Password") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.85f),
        )

        if (authState is AuthorisationViewModel.AuthState.Error) {
            Text(
                text = authState.message,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (authState is AuthorisationViewModel.AuthState.Loading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = {
                if (email.value.isNotBlank() && password.value.isNotBlank() && username.value.isNotBlank()) {
                    viewModel.signup(
                        username = username.value,
                        password = password.value,
                        email = email.value
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3366CC)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(55.dp)
                .shadow(4.dp, RoundedCornerShape(15.dp))
        ) {
            Text("SIGN UP", fontWeight = FontWeight.Bold, color = Color.White)
        }




        Spacer(modifier = Modifier.height(70.dp))
    }
}