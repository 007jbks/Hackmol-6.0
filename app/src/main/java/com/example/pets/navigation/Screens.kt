package com.example.pets.navigation

sealed class Screens (val route :String){
    object splashscreen:Screens("splashscreen")
    object choosescreen:Screens("choosescreen")
    object rehome:Screens("rehome")
    object pet:Screens("pet")
    object abandoned:Screens("abandoned")
    object screen6:Screens("screen6")
    object screen7:Screens("screen7")
    object screen8:Screens("screen8")
    object signin:Screens(route = "signin")
    object signup:Screens(route = "signup")






}