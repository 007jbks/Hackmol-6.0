package com.example.pets

import com.example.pets.connectingfiles.*
import com.example.pets.petlisting.ListingResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("signup")
    suspend fun signup(@Body signup: Signup): Response<SignupResponse>

    @POST("login")
    suspend fun login(@Body login: Login): Response<LoginResponse>

    @Multipart
    @POST("sell")
    suspend fun listYourPet(
        @Part("name") name: RequestBody,
        @Part("species") species: RequestBody,
        @Part("token") token: RequestBody,
        @Part("breed") breed: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("age") age: RequestBody,
        @Part("health") health: RequestBody,
        @Part("description") description: RequestBody,
        @Part("color_markings") colorMarkings: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<ListingResponse>

    @POST("interest")
    suspend fun interest(@Body interest: Interest): Response<InterestResponse>

    @POST("buy")
    suspend fun buy(@Body buy: Buy): Response<BuyResponse>

    @Multipart
    @POST("facial_match")
    suspend fun uploadFacialMatch(
        @Part file: MultipartBody.Part,
        @Part("token") token: RequestBody
    ): Response<Facial_MatchResponse>

    @POST("transfer")
    suspend fun transfer(@Body transfer: Transfer): Response<TransferResponse>

    @POST("update")
    suspend fun update(@Body update: Update): Response<UpdateResponse>

    @POST("report")
    suspend fun report(@Body report: Report): Response<ReportResponse>

    @POST("sendChat")
    suspend fun sendChat(@Body sendChat: SendChat): Response<SendChatResponse>
}