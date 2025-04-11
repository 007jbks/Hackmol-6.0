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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pets.R


@Composable
fun LoginScreenngo(viewModel: AuthorisationViewModel,navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE6F0)) // Light pink background
            .padding(start = 24.dp,end =24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Placeholder for dog image
        Image (painter = painterResource(R.drawable.dog), contentDescription = "dog",Modifier.size(300.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // Speech bubble text

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Paw Control",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3366CC) // Blue color
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Meet. Match. Master pet parenthood",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9900) // Orange color
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Work-Email id") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.85f),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Create Password") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.85f),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.login(password= password.toString(),email= email.toString()) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3366CC)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(55.dp)
                .shadow(4.dp, RoundedCornerShape(15.dp))
        ) {
            Text("SIGN IN", fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text("Dont have an account ")
            Text("  Signup" , modifier =   Modifier.clickable {  }, color = Color(0xFF3366CC))}
        Spacer(modifier = Modifier.height(70.dp))
    }
}

@Composable
fun SignupScreenngo(viewModel: AuthorisationViewModel,navController: NavController) {
    val work_email_id= remember { mutableStateOf("") }
    val create_password = remember { mutableStateOf("") }
    val registration_Number = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE6F0)) // Light pink background
            .padding(start = 24.dp,end =24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Placeholder for dog image
        Image (painter = painterResource(R.drawable.dog), contentDescription = "dog",Modifier.size(250.dp))

        Spacer(modifier = Modifier.height(10.dp))

        // Speech bubble text

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Paw Control",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3366CC) // Blue color
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Meet. Match. Master pet parenthood",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9900) // Orange color
        )

        Spacer(modifier = Modifier.height(26.dp))

        OutlinedTextField(
            value = work_email_id.value,
            onValueChange = { work_email_id.value = it },
            label = { Text("Enter Email") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.85f),
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = registration_Number.value,
            onValueChange = { create_password.value = it },
            label = { Text("Enter Username") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.85f),
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = create_password.value,
            onValueChange = { create_password.value = it },
            label = { Text("Enter Password") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.85f),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.signup(password= create_password.toString(),email= work_email_id.toString(), username = registration_Number.toString()) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3366CC)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(55.dp)
                .shadow(4.dp, RoundedCornerShape(15.dp))
        ) {
            Text("SIGN UP", fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))


        Spacer(modifier = Modifier.height(70.dp))
    }
}