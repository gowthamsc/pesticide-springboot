package com.pestManage.pesticideMange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Document("admin")
data class Admin(
    @Id
    @field:Email(message = "Email should be valid")
    var email:String,
    @field: Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$", message=" Should contain atleast one character,uppercase,lowercase,special characters,digits and be in 8 charcters")
    var password:String,
    @field:NotBlank(message = "Name can't be blank, it's mandatory")
    var name:String,
    @field: Pattern(regexp="^\$|[0-9]{10}", message = "Should be valid phone number")
    var phoneNo:String,
    @field: Valid
    var address:AdminAddress?,
    @JsonIgnore
    var type:String? ="Admin",
    @JsonIgnore
    var isLoggedIn:Boolean? = false,
    var lastLogin: String? =""
)