package com.example.pets.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pets.onboarding.PawControlScreen
import com.example.pets.onboarding.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val application = context.applicationContext as Activity
    val startDestination = Screens.splashscreen.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable("choosescreen"){
            PawControlScreen()
        }
        composable(Screens.splashscreen.route){
            SplashScreen(navController)
        }
    }
}