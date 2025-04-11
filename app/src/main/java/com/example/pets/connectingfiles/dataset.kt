package com.example.pets.connectingfiles

data class SignupResponse(
    val access_token: String,
    val token_type: String
)
data class Signup(
    val username:String,
    val password:String,
    val email:String,
    val address: String,
    val registration_number: Int?
        )
data class LoginResponse(
    val access_token: String,
    val token_type: String
)
data class Login(
    val email:String,
    val password:String,

)
data class Sell(
    val name: String,
    val species: String,
    val token: String,
    val file: String,
    val breed: String,
    val gender: String,
    val weight: Int,
    val age: Int,
    val health: String,
    val description: String,
    val color_markings: String
)

data class SellResponse(
    val message: String,
    val traits: Traits
)
data class Traits(
    val face_shape: String,
    val eye_size: String,
    val nose_shape: String,
    val ear_type: String,
    val fur: String
)

data class Interest(
    val species: String,
    val token: String,
    val breed: String,
    val gender: String,
    val weight_range:String,
    val age_range:String,
    val color_markings: String
)
data class InterestResponse(
    val matches: List<Match>
)

data class Match(
    val id: Int,
    val name: String,
    val species: String,
    val breed: String,
    val gender: String,
    val weight: Int,
    val age: Int,
    val color_markings: String,
    val health: String,
    val description: String,
    val image_url: String
)

data class Buy(
    val id: Int,
    val token: String
)

data class BuyResponse(
    val message: String
)

data class Facial_Match(
    val file: String,
    val token: String
)

data class Facial_MatchResponse(
    val matched_pet: MatchedPet
)
data class MatchedPet(
    val name: String,
    val id: Int,
    val image: String,
    val traits: Traits,
    val description: String
)


data class Transfer(
    val token: String,
    val new_owner_email_id:String,
    val pet_name: String
)
data class TransferResponse(
    val message: String
)



data class Report(
    val species: String,
    val breed: String,
    val gender: String,
    val markings: String,
    val age: Int,
    val features: String,
    val health: String,
    val behavior:String,
    val location:String,
    val token: String,
)

data class ReportResponse(
    val message: String
)

data class SendChat(
    val query: String
)

data class SendChatResponse(
    val bot_says: String
)

data class Update(
    val pet_id: Int,
    val date: String,
    val file: String,
    val token:String
)

data class UpdateResponse(
    val message: String
)
