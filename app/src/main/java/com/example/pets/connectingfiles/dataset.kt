package com.example.pets.connectingfiles

data class SignupResponse(
    val access_token: String,
    val token_type: String
)
data class Signup(
        val username :String  ,
        val password:String,
        val email:String
        )
data class LoginResponse(
    val access_token: String,
    val token_type: String
)
data class Login(
    val email:String,
    val password:String,

)