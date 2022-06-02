package com.pestManage.pesticideMange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.xml.crypto.Data

@Document("delivery")
data class Delivery(
    @Id
    @field:Email(message = "Email should be valid")
    var email:String,
    @JsonIgnore
    var type:String? = "DeliverPerson",
    var password:String?,
    @field:NotBlank(message = "Name can't be blank, it's mandatory")
    var name:String,
    @field: Pattern(regexp="^\$|[0-9]{10}", message = "Should be valid phone number")
    var phoneNo:String,
    @field: Valid
    var deliverAddress:DeliverAddress?,
    @Transient
    @JsonIgnore
    var isLoggedIn:Boolean? = false,
    var lastLogin: String? =""
)