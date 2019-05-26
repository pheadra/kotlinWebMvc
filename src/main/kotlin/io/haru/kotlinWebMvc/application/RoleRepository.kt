package io.haru.kotlinWebMvc.application

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(roleName: RoleName): Optional<Role>
}