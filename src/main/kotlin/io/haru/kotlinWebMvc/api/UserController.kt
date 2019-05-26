package io.haru.kotlinWebMvc.api

import io.haru.kotlinWebMvc.api.payload.UserIdentityAvailability
import io.haru.kotlinWebMvc.api.payload.UserSummary
import io.haru.kotlinWebMvc.application.UserRepository
import io.haru.kotlinWebMvc.config.security.CurrentUser
import io.haru.kotlinWebMvc.config.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {
    // 일부러 Autowired 써봄. 주로 생성자 주입을 많이 쓰지만.. 이렇게도 가능함
    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser(@CurrentUser currentUser: UserPrincipal): UserSummary {
        return UserSummary(currentUser.id, currentUser.username, currentUser.name)
    }

    @GetMapping("/user/checkUsernameAvailability")
    fun checkUsernameAvailability(@RequestParam(value = "username") username: String): UserIdentityAvailability {
        val isAvailable = !userRepository.existsByUsername(username)
        return UserIdentityAvailability(isAvailable)
    }

    @GetMapping("/user/checkEmailAvailability")
    fun checkEmailAvailability(@RequestParam(value = "email") email: String): UserIdentityAvailability {
        val isAvailable = !userRepository.existsByEmail(email)
        return UserIdentityAvailability(isAvailable)
    }
}