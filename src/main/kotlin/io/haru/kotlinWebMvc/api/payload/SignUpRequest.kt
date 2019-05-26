package io.haru.kotlinWebMvc.api.payload

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class SignUpRequest(
    @NotBlank
    @Size(min = 4, max = 40)
    val name: String,

    @NotBlank
    @Size(min = 3, max = 15)
    val username: String,

    @NotBlank
    @Size(max = 40)
    @Email
    val email: String,

    @NotBlank
    @Size(min = 6, max = 20)
    val password: String
)