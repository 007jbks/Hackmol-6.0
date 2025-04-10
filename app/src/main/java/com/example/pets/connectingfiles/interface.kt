package com.example.pets

import com.example.pets.connectingfiles.Login
import com.example.pets.connectingfiles.LoginResponse
import com.example.pets.connectingfiles.SignupResponse
import com.example.pets.connectingfiles.Signup
import com.example.pets.petlisting.ListingResponse

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    // For successful string response
   @POST("/signup")
   suspend fun Signup(@Body signup:Signup):Response<SignupResponse>

   @Multipart
   @POST("/sell")
   suspend fun ListYourPet(@Part("name") name: RequestBody,
                           @Part("species") species: RequestBody,
                           @Part("token") token: RequestBody,
                           @Part file: MultipartBody.Part):Response<ListingResponse>
    @POST("/login")
    suspend fun Login(@Body login: Login):Response<LoginResponse>



}