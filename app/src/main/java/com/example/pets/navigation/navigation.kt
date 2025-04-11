package com.example.pets.navigation

import TinderApp
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pets.ChatBotScreen
import com.example.pets.R
import com.example.pets.abondonedapp.PetFormScreen
import com.example.pets.abondonedapp.PetFormScreen2
import com.example.pets.connectingfiles.AuthorisationViewModel
import com.example.pets.connectingfiles.LoginScreen
import com.example.pets.connectingfiles.SignupScreen
import com.example.pets.connectingfiles.SignupScreenngo
import com.example.pets.onboarding.PawControlScreen
import com.example.pets.onboarding.SplashScreen
import com.example.pets.onboarding.StartingScreen
import com.example.pets.pawmatch.AddPhotoScreen
import com.example.pets.pawmatch.PawMatchScreen

// Extension function to safely find activity
fun Context.findActivity(): Activity? {


    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

@Composable
fun Navigation() {
    val authorisationViewModel: AuthorisationViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.splashscreen.route) {

        composable(Screens.splashscreen.route) {
            SplashScreen(navController)
        }

        composable(Screens.whyint.route) {
            PawControlScreen(navController)
        }

        composable(Screens.pawcontrol.route) {
            SignupScreen(
                viewModel = authorisationViewModel,
                navController = navController
            )
        }

        composable(Screens.choose.route) {
            StartingScreen(navController)
        }

        composable(Screens.pawmatch.route) {
            PawMatchScreen(navController)
        }

        composable(Screens.anidetal.route) {
            Image(
                painter = painterResource(R.drawable.peta),
                contentDescription = "background",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate(Screens.detailani2.route) }
            )
        }

        composable(Screens.detailani2.route) {
            Image(
                painter = painterResource(R.drawable.preptime),
                contentDescription = "background",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate(Screens.chat.route) }
            )
        }

        composable(Screens.rehome.route) {
            Image(
                painter = painterResource(R.drawable.rehome),
                contentDescription = "background",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate(Screens.rehome.route) }
            )
        }

        composable(Screens.ngosignup.route) {
            Text("Under Progress")
        }

        composable(Screens.petrej1.route) {
            PetFormScreen(navController)
        }

        composable(Screens.petrej2.route) {
            PetFormScreen2(navController)
        }

        composable(Screens.tinder.route) {
            TinderApp(navController)
        }

        composable(Screens.chat.route) {
            ChatBotScreen()
        }
        composable(Screens.login.route){
            LoginScreen(viewModel = authorisationViewModel,
                navController = navController)
        }
        composable(Screens.ngosignup.route){
            SignupScreenngo(authorisationViewModel,navController)
        }
        composable(Screens.ngodash.route){
            PawControlScreen(navController)
        }
        composable(Screens.urpic.route){
            var selectedImage by remember { mutableStateOf<Bitmap?>(null) }

            // To get the Activity context for starting the image picker intent
            val context = LocalContext.current

            // Launcher for image picker
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                uri?.let {
                    // Convert URI to Bitmap
                    val bitmap = try {
                        context.contentResolver.openInputStream(uri)?.use { stream ->
                            BitmapFactory.decodeStream(stream)
                        }
                    } catch (e: Exception) {
                        null
                    }
                    selectedImage = bitmap
                }
            }

            Column(
                modifier =  Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFE4EC)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(40.dp))
                Text("ADD YOUR PHOTO", color = Color(0xFF3B6CC6), fontWeight = FontWeight.Bold, fontSize = 24.sp)

                Spacer(Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .border(2.dp, Color.Blue, shape = RoundedCornerShape(16.dp))
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImage != null) {
                        Image(
                            bitmap = selectedImage!!.asImageBitmap(),
                            contentDescription = "Selected photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add photo",
                            tint = Color.Blue,
                            modifier = Modifier.size(48.dp))
                    }
                }

                Spacer(Modifier.height(32.dp))

                // Add illustrated cat holding phone here

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {navController.navigate(Screens.pawmatch.route)},
                    colors = ButtonDefaults.buttonColors(Color(0xFF3B6CC6)),
                    elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
                ) {
                    Text("CONTINUE", color = Color.White)
                }
            }
        }

    }
}
