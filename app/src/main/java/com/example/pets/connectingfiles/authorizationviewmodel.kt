package com.example.pets.connectingfiles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.token
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class AuthorisationViewModel : ViewModel() {
    private val repository = ApiRepository()

    fun signup(username: String, password: String,email: String) {
        viewModelScope.launch {
            repository.signup(username, password,email)
                .onSuccess { authtocken ->
                    // Handle success
                    Log.d("signing up", "signup: successful - $authtocken")
                     token =authtocken.access_token
                    Log.d("tocken", token)
                }
                .onFailure { exception ->

                    // Handle failure
                    Log.d("signing up", "signup: failed - ${exception}")
                    when (exception) {
                        is HttpException -> {
                            val errorCode = exception.code()
                            val errorBody = exception.response()?.errorBody()?.string()
                            Log.e("SIGNUP", "HTTP Error Code: $errorCode")
                            Log.e("SIGNUP", "Error Response: ${errorBody ?: "No error body"}")
                        }
                        is IOException -> {
                            Log.e("SIGNUP", "Network Error: ${exception.message}")
                        }
                        else -> {
                            Log.e("SIGNUP", "Unexpected Error: ${exception.javaClass.simpleName}")
                        }
                }
        }
    }}
    fun login( password: String,email: String) {
        viewModelScope.launch {
            repository.login(

                password = password,
                email = email
            )
                .onSuccess { authtocken ->
                    // Handle success
                    Log.d("signing up", "signup: successful - $authtocken")
                    token =authtocken.access_token
                    Log.d("tocken", token)
                }
                .onFailure { exception ->

                    // Handle failure
                    Log.d("signing up", "signup: failed - ${exception}")
                    when (exception) {
                        is HttpException -> {
                            val errorCode = exception.code()
                            val errorBody = exception.response()?.errorBody()?.string()
                            Log.e("SIGNUP", "HTTP Error Code: $errorCode")
                            Log.e("SIGNUP", "Error Response: ${errorBody ?: "No error body"}")
                        }
                        is IOException -> {
                            Log.e("SIGNUP", "Network Error: ${exception.message}")
                        }
                        else -> {
                            Log.e("SIGNUP", "Unexpected Error: ${exception.javaClass.simpleName}")
                        }
                    }
                }
        }}
}// Renamed to avoid conflict with Kotlin's built-in Result class
