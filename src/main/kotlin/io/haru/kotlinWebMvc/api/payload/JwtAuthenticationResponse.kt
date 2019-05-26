package io.haru.kotlinWebMvc.api.payload

data class JwtAuthenticationResponse(
    val accessToken: String,
    val tokenType : String = "Bearer"
)