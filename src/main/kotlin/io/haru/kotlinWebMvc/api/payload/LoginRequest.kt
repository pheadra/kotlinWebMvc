package io.haru.kotlinWebMvc.api.payload

import javax.validation.constraints.NotBlank

data class LoginRequest(
    @NotBlank
    val usernameOrEmail: String,

    @NotBlank
    val password: String
)
