
package com.example.pets.petlisting
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.connectingfiles.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// UI state class to represent different states of the pet listing process
data class PetListingUiState(
    val petName: String = "",
    val petSpecies: String = "",
    val photoUri: Uri? = null,
    val photoPath: String? = null,
    val isLoading: Boolean = false,
    val success: ListingResponse? = null,
    val error: String? = null
)



class PetListingViewModel : ViewModel() {
    private val repository = ApiRepository()

    // MutableStateFlow to store the current UI state
    private val _uiState = MutableStateFlow(PetListingUiState())

    // Public StateFlow exposed to the UI
    val uiState: StateFlow<PetListingUiState> = _uiState.asStateFlow()

    // Update pet name
    fun updatePetName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(petName = name)
        }
    }

    // Update pet species
    fun updatePetSpecies(species: String) {
        _uiState.update { currentState ->
            currentState.copy(petSpecies = species)
        }
    }

    // Update photo Uri
    fun updatePhotoUri(uri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(photoUri = uri)
        }
    }

    // Update photo path
    fun updatePhotoPath(path: String) {
        _uiState.update { currentState ->
            currentState.copy(photoPath = path)
        }
    }

    // Function to check if all required fields are filled
    fun isFormValid(): Boolean {
        return _uiState.value.petName.isNotBlank() &&
                _uiState.value.petSpecies.isNotBlank() &&
                _uiState.value.photoPath != null
    }

    // Function to create a pet listing
    fun createPetListing(token: String) {
        if (!isFormValid()) {
            _uiState.update { currentState ->
                currentState.copy(error = "Please fill all fields and select a photo")
            }
            return
        }

        viewModelScope.launch {
            // Set loading state
            _uiState.update { currentState ->
                currentState.copy(isLoading = true)
            }

            try {
                // Call the pet listing function with the current values
                val result = repository.petListing(
                    name = _uiState.value.petName,
                    species = _uiState.value.petSpecies,
                    token = token,
                    photo = _uiState.value.photoPath!!
                )

                // Update UI state based on result
                result.fold(
                    onSuccess = { response ->
                        Log.d("token",token)
                        Log.d("PetListingViewModel", "Pet listing success: $response")
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                success = response,
                                error = null
                            )
                        }
                    },
                    onFailure = { exception ->
                        Log.e("PetListingViewModel", "Pet listing failed", exception)
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = exception.message ?: "Unknown error occurred",
                                success = null
                            )
                        }
                    }
                )
            } catch (e: Exception) {
                Log.e("PetListingViewModel", "Unexpected error during pet listing", e)
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred",
                        success = null
                    )
                }
            }
        }
    }

    // Reset the UI state
    fun resetState() {
        _uiState.value = PetListingUiState()
    }

    // Clear just the error message
    fun clearError() {
        _uiState.update { currentState ->
            currentState.copy(error = null)
        }
    }
}
