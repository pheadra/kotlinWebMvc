package io.haru.kotlinWebMvc.api

import io.haru.kotlinWebMvc.api.payload.ApiResponse
import io.haru.kotlinWebMvc.api.payload.JwtAuthenticationResponse
import io.haru.kotlinWebMvc.api.payload.LoginRequest
import io.haru.kotlinWebMvc.api.payload.SignUpRequest
import io.haru.kotlinWebMvc.application.RoleName
import io.haru.kotlinWebMvc.application.RoleRepository
import io.haru.kotlinWebMvc.application.User
import io.haru.kotlinWebMvc.application.UserRepository
import io.haru.kotlinWebMvc.config.security.JwtTokenProvider
import io.haru.kotlinWebMvc.exception.AppException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authenticationManager: AuthenticationManager,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val passwordEncoder: PasswordEncoder,
    val tokenProvider: JwtTokenProvider
) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.usernameOrEmail,
                loginRequest.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider.generateToken(authentication)
        return ResponseEntity.ok(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<*> {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            return ResponseEntity(
                ApiResponse(false, "Username is already taken!"),
                HttpStatus.BAD_REQUEST
            )
        }

        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity(
                ApiResponse(false, "Email Address already in use!"),
                HttpStatus.BAD_REQUEST
            )
        }

        // Creating user's account
        // id에는 null을 넣어야 해서 name parameter를 씀
        val user = User(
            name = signUpRequest.name,
            username = signUpRequest.username,
            email = signUpRequest.email,
            password = signUpRequest.password
        )

        user.password = passwordEncoder.encode(user.password)

        val userRole = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow { AppException("User Role not set.") }

        user.roles = setOf(userRole)

        val result = userRepository.save(user)

        val location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/users/{username}")
            .buildAndExpand(result.username).toUri()

        return ResponseEntity.created(location).body<Any>(ApiResponse(true, "User registered successfully"))
    }
}