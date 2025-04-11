package com.example.pets.connectingfiles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.token
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class AuthorisationViewModel : ViewModel() {
    private val repository = ApiRepository()

    // State for login/signup results
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val token: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    fun signup(username: String, password: String, email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                repository.signup(username, password, email)
                    .onSuccess { authToken ->
                        token = authToken.access_token
                        _authState.value = AuthState.Success(authToken.access_token)
                        Log.d("Auth", "Signup successful - $token")
                    }
                    .onFailure { exception ->
                        val errorMessage = when (exception) {
                            is HttpException -> {
                                val errorBody = exception.response()?.errorBody()?.string()
                                errorBody ?: "HTTP Error: ${exception.code()}"
                            }
                            is IOException -> "Network Error: ${exception.message}"
                            else -> "Error: ${exception.message}"
                        }
                        _authState.value = AuthState.Error(errorMessage)
                        Log.e("Auth", "Signup failed: $errorMessage")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Unexpected error: ${e.message}")
                Log.e("Auth", "Signup exception: ${e.message}")
            }
        }
    }

    fun login(password: String, email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                repository.login(password = password, email = email)
                    .onSuccess { authToken ->
                        token = authToken.access_token
                        _authState.value = AuthState.Success(authToken.access_token)
                        Log.d("Auth", "Login successful - $token")
                    }
                    .onFailure { exception ->
                        val errorMessage = when (exception) {
                            is HttpException -> {
                                val errorBody = exception.response()?.errorBody()?.string()
                                errorBody ?: "HTTP Error: ${exception.code()}"
                            }
                            is IOException -> "Network Error: ${exception.message}"
                            else -> "Error: ${exception.message}"
                        }
                        _authState.value = AuthState.Error(errorMessage)
                        Log.e("Auth", "Login failed: $errorMessage")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Unexpected error: ${e.message}")
                Log.e("Auth", "Login exception: ${e.message}")
            }
        }
    }
}