package io.haru.kotlinWebMvc.config.security

import org.springframework.security.core.annotation.AuthenticationPrincipal

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@AuthenticationPrincipal
annotation class CurrentUser
