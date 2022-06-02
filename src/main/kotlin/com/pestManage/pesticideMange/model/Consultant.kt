package com.pestManage.pesticideMange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Document("consultant")
data class Consultant(
    @Id
    @field:Email(message = "Email should be valid")
    var consultantEmail:String,
    @JsonIgnore
    var type:String? = "Consultant",
    @field: Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$", message=" Should contain atleast one character,uppercase,lowercase,special characters,digits and be in 8 charcters")
    var consultantPassword:String?,
    @field:NotBlank(message = "Name can't be blank, it's mandatory")
    var consultantName:String,
    @field: Pattern(regexp="^\$|[0-9]{10}", message = "Should be valid phone number")
    var consultantPhoneNo:String,
    @JsonIgnore
    var isLoggedIn:Boolean? = false,
    @field: Valid
    var consultantAddress:ConsultantAddress,
    var lastLogin: String?=""
)