package com.example.pets.petlisting

data class pet_listing(
    val name :String,
    val species :String,
    val token :String,
    val file :String
)
data class ListingResponse(
    val response : String
)
