package com.example.pets.connectingfiles.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.ChatMessage
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

class ChatBotViewModel : ViewModel() {

    // API service for chat communication
    private val chatApiService: ChatApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://hackmol-6-0.onrender.com/") // Your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApiService::class.java)
    }

    // Messages state
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Error state
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /**
     * Sends a user message to the chat API and handles the response
     */
    fun sendMessage(userInput: String) {
        if (userInput.isBlank()) return

        // Create and add user message
        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            content = userInput,
            isUser = true
        )

        _messages.value = _messages.value + userMessage
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // Make API call
                val response = chatApiService.sendChat(SendChat(userInput))

                if (response.isSuccessful) {
                    // Handle the response - note that we're checking both potential field names
                    val botResponse = response.body()?.botSays ?: "Sorry, I couldn't process that"

                    // Create and add bot message
                    val botMessage = ChatMessage(
                        id = UUID.randomUUID().toString(),
                        content = botResponse,
                        isUser = false
                    )

                    _messages.value = _messages.value + botMessage
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
                e.printStackTrace() // Log the error for debugging
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Clear any error message
     */
    fun clearError() {
        _errorMessage.value = null
    }

    /**
     * Use this method for development/testing without API
     */
    fun sendMessageOffline(userInput: String) {
        if (userInput.isBlank()) return

        // Create and add user message
        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            content = userInput,
            isUser = true
        )

        _messages.value = _messages.value + userMessage
        _isLoading.value = true

        viewModelScope.launch {
            kotlinx.coroutines.delay(800) // Simulate network delay

            // Create and add simulated bot message
            val botResponse = generateBotResponse(userInput)
            val botMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                content = botResponse,
                isUser = false
            )

            _messages.value = _messages.value + botMessage
            _isLoading.value = false
        }
    }

    private fun generateBotResponse(userInput: String): String {
        val responses = listOf(
            "I understand you're asking about \"$userInput\". Let me help with that.",
            "Thanks for your message! Regarding \"$userInput\", here's what I know...",
            "Interesting point about \"$userInput\". Here's some information that might help.",
            "I've processed your request about \"$userInput\". Here's my response.",
            "Let me think about \"$userInput\"... Here's what I can tell you."
        )
        return responses.random()
    }
}

// API Service interface for Retrofit
interface ChatApiService {
    @retrofit2.http.POST("/sendChat")
    suspend fun sendChat(@retrofit2.http.Body sendChat: SendChat): retrofit2.Response<SendChatResponse>
}

// Data classes for API communication
data class SendChat(
    val query: String
)

data class SendChatResponse(
    @SerializedName("bot says:")
    val botSays: String
)