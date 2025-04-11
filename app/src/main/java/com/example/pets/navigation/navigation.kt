package com.example.pets.navigation

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pets.R
import com.example.pets.abondonedapp.PetFormScreen
import com.example.pets.abondonedapp.PetFormScreen2
import com.example.pets.onboarding.PawControlScreen
import com.example.pets.onboarding.SplashScreen
import com.example.pets.onboarding.StartingScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val application = context.applicationContext as Activity
    val startDestination = Screens.splashscreen.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.whyint.route){
            PawControlScreen()
        }
        composable(Screens.splashscreen.route){
            SplashScreen(navController)
        }
        composable(Screens.choose.route){
            StartingScreen(navController)
        }
        composable(Screens.pawmatch.route){
            Image(painter = painterResource(R.drawable.crying_doggy), contentDescription = "background", modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate(Screens.tinder.route) })
        }
        composable(Screens.anidetal.route){
            Image(painter = painterResource(R.drawable.peta), contentDescription = "background", modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate(Screens.detailani2.route) })
        }
        composable(Screens.detailani1.route){
            Image(painter = painterResource(R.drawable.preptime), contentDescription = "background", modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate(Screens.chat.route) })
        }
        composable(Screens.whyint.route){
            Image(painter = painterResource(R.drawable.rehome), contentDescription = "background", modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate(Screens.rehome.route) })
        }
        composable(Screens.pawmatch.route){
            Image(painter = painterResource(R.drawable.splashs ), contentDescription = "background", modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate(Screens.tinder.route) })
        }
        composable(Screens.ngosignup.route){

        }
        composable(Screens.petrej1.route){
            PetFormScreen(navController)
        }
        composable(Screens.petrej2.route){
            PetFormScreen2(navController)
        }




    }
}