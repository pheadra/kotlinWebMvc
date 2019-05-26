package io.haru.kotlinWebMvc.application

import io.haru.kotlinWebMvc.application.audit.DateAudit
import org.hibernate.annotations.NaturalId
import java.util.HashSet
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * entity에서도
 * 변경 가능하면 var, 불변이면 val
 *
 * value object 면 다 val 로 하는게 좋아 보임. (immutable 이기 때문에)
 */
@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["username"]),
        UniqueConstraint(columnNames = ["email"])
    ]
)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // 값이 바뀌진 않고 자동 생성될거라서..

    @NotBlank
    @Size(max = 40)
    var name: String,

    @NotBlank
    @Size(max = 15)
    var username: String,

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    var email: String,

    @NotBlank
    @Size(max = 100)
    var password: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: Set<Role> = HashSet()
) : DateAudit()