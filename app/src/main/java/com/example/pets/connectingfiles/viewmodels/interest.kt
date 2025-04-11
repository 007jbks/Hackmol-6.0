package com.example.pets.connectingfiles.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.connectingfiles.ApiRepository
import com.example.pets.connectingfiles.Interest
import com.example.pets.connectingfiles.InterestResponse
import com.example.pets.connectingfiles.Match
import com.example.pets.token
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

class Interest1 : ViewModel() {
    private val repository = ApiRepository()

    // LiveData to observe the matches
    private val _matches = MutableLiveData<List<Match>>()
    val matches: LiveData<List<Match>> = _matches

    // State for loading and error handling
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun searchPet(species: String, breed: String, gender: String, weight_range: String, age_range: String, color_markings: String) {
        _isLoading.value = true
        _errorMessage.value = ""

        viewModelScope.launch {
            repository.interest(
                interest = Interest(
                    species,
                    token,
                    breed,
                    gender,
                    weight_range,
                    age_range,
                    color_markings
                )
            )
                .onSuccess { response: InterestResponse ->
                    // Handle success
                    _matches.value = response.matches
                    _isLoading.value = false
                    Log.d("SEARCH_PET", "Success: Found ${response.matches.size} matches")
                }
                .onFailure { exception ->
                    // Handle failure
                    _isLoading.value = false
                    Log.d("SEARCH_PET", "Failed - ${exception}")

                    when (exception) {
                        is HttpException -> {
                            val errorCode = exception.code()
                            val errorBody = exception.response()?.errorBody()?.string()
                            _errorMessage.value = "Server error: $errorCode"
                            Log.e("SEARCH_PET", "HTTP Error Code: $errorCode")
                            Log.e("SEARCH_PET", "Error Response: ${errorBody ?: "No error body"}")
                        }
                        is IOException -> {
                            _errorMessage.value = "Network error: ${exception.message}"
                            Log.e("SEARCH_PET", "Network Error: ${exception.message}")
                        }
                        else -> {
                            _errorMessage.value = "Unexpected error occurred"
                            Log.e("SEARCH_PET", "Unexpected Error: ${exception.javaClass.simpleName}")
                        }
                    }
                }
        }
    }
}

// Data classes
data class Interest(
    val species: String,
    val token: String,
    val breed: String,
    val gender: String,
    val weight_range: String,
    val age_range: String,
    val color_markings: String
)

data class InterestResponse(
    val matches: List<Match>
)

data class Match(
    val id: String,
    val name: String,
    val species: String,
    val breed: String,
    val gender: String,
    val age: String,
    val weight: String,
    val color: String,
    val imageUrl: String? = null,
    val description: String? = null,
    val location: String? = null
)