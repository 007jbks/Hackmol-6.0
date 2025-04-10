package com.example.pets.connectingfiles

import com.example.pets.RetrofitClient
import com.example.pets.petlisting.ListingResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.IOException
import java.io.File


class ApiRepository {
        suspend fun signup(username: String, password: String, email: String): Result<SignupResponse> {
            return try {
                val signupRequest = Signup(username = username, password = password, email=email)
                val response = RetrofitClient.retrofit.Signup(
                    signup =signupRequest
                )
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    val error = parseError(response.errorBody())
                    Result.failure(Exception(error))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        private fun parseError(errorBody: ResponseBody?): String {
            return try {
                errorBody?.string() ?: "Unknown error occurred"
            } catch (e: IOException) {
                "Error parsing error response"
            }
        }
    suspend fun petListing(name: String, species: String, token: String, photo: String): Result<ListingResponse> {
        return try {
            // Create a file from the photo path
            val file = File(photo)

            // Create RequestBody instances for text fields
            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val speciesBody = species.toRequestBody("text/plain".toMediaTypeOrNull())
            val tokenBody = token.toRequestBody("text/plain".toMediaTypeOrNull())

            // Create MultipartBody.Part for the file
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            // Make the API call
            val response = RetrofitClient.retrofit.ListYourPet(
                name = nameBody,
                species = speciesBody,
                token = tokenBody,
                file = filePart
            )

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val error = parseError(response.errorBody())
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
    suspend fun login( password: String, email: String): Result<LoginResponse> {
        return try {
            val LoginRequest = Login( password = password, email=email)
            val response = RetrofitClient.retrofit.Login(LoginRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val error = parseError(response.errorBody())
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    }


