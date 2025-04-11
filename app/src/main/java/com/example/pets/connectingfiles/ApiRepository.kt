package com.example.pets.connectingfiles


import android.net.Uri
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
    private fun parseError(errorBody: ResponseBody?): String {
        return try {
            errorBody?.string() ?: "Unknown error occurred"
        } catch (e: IOException) {
            "Error parsing error response"
        }
    }

    // Existing functions
    suspend fun signup(username: String, password: String, email: String): Result<SignupResponse> {
        return try {
            val response = RetrofitClient.retrofit.signup(Signup(username, password, email, address = "", registration_number = null))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = RetrofitClient.retrofit.login(Login(email, password))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun listYourPet(
        name: String,
        species: String,
        token: String,
        breed: String,
        gender: String,
        weight: Int,
        age: Int,
        health: String,
        description: String,
        colorMarkings: String,
        fileUri: Uri,
        contentResolver: android.content.ContentResolver
    ): Result<ListingResponse> {
        return try {
            val inputStream = contentResolver.openInputStream(fileUri)
            val file = File.createTempFile("pet_image", ".jpg")
            inputStream?.use { it.copyTo(file.outputStream()) }

            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val speciesBody = species.toRequestBody("text/plain".toMediaTypeOrNull())
            val tokenBody = token.toRequestBody("text/plain".toMediaTypeOrNull())
            val breedBody = breed.toRequestBody("text/plain".toMediaTypeOrNull())
            val genderBody = gender.toRequestBody("text/plain".toMediaTypeOrNull())
            val weightBody = weight.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val ageBody = age.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val healthBody = health.toRequestBody("text/plain".toMediaTypeOrNull())
            val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val colorMarkingsBody = colorMarkings.toRequestBody("text/plain".toMediaTypeOrNull())

            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            val response = RetrofitClient.retrofit.listYourPet(
                nameBody, speciesBody, tokenBody, breedBody, genderBody,
                weightBody, ageBody, healthBody, descriptionBody, colorMarkingsBody, filePart
            )

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun interest(interest: Interest): Result<InterestResponse> {
        return try {
            val response = RetrofitClient.retrofit.interest(interest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buy(buy: Buy): Result<BuyResponse> {
        return try {
            val response = RetrofitClient.retrofit.buy(buy)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // New functions for remaining endpoints
    suspend fun uploadFacialMatch(
        fileUri: Uri,
        token: String,
        contentResolver: android.content.ContentResolver
    ): Result<Facial_MatchResponse> {
        return try {
            val inputStream = contentResolver.openInputStream(fileUri)
            val file = File.createTempFile("facial_match", ".jpg")
            inputStream?.use { it.copyTo(file.outputStream()) }

            val tokenBody = token.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            val response = RetrofitClient.retrofit.uploadFacialMatch(filePart, tokenBody)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun transfer(transfer: Transfer): Result<TransferResponse> {
        return try {
            val response = RetrofitClient.retrofit.transfer(transfer)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun update(update: Update): Result<UpdateResponse> {
        return try {
            val response = RetrofitClient.retrofit.update(update)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun report(report: Report): Result<ReportResponse> {
        return try {
            val response = RetrofitClient.retrofit.report(report)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendChat(sendChat: SendChat): Result<SendChatResponse> {
        return try {
            val response = RetrofitClient.retrofit.sendChat(sendChat)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(parseError(response.errorBody())))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Helper function to create temp file from Uri
    private fun createTempFileFromUri(
        uri: Uri,
        contentResolver: android.content.ContentResolver,
        prefix: String = "file",
        suffix: String = ".tmp"
    ): File {
        val inputStream = contentResolver.openInputStream(uri)
        val file = File.createTempFile(prefix, suffix)
        inputStream?.use { it.copyTo(file.outputStream()) }
        return file
    }
}