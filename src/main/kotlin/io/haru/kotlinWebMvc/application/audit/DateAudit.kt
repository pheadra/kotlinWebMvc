package io.haru.kotlinWebMvc.application.audit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(value = ["createdAt", "updatedAt"], allowGetters = true)
abstract class DateAudit : Serializable {

    @CreatedDate
    var createdAt: Instant? = null

    @LastModifiedDate
    var updatedAt: Instant? = null

}
