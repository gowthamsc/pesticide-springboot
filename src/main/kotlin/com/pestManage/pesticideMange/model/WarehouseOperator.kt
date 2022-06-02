package com.pestManage.pesticideMange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
@Document("warehouseOperator")
data class WarehouseOperator(
    @Id
    @field:Email(message = "Email should be valid")
    var operatorEmail:String,
    @JsonIgnore
    var type:String? = "WarehouseOperator",
    @field: Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$", message=" Should contain atleast one character,uppercase,lowercase,special characters,digits and be in 8 charcters")
    var operatorPassword:String?,
    @field:NotBlank(message = "Name can't be blank, it's mandatory")
    var operatorName:String,
    @field: Pattern(regexp="^\$|[0-9]{10}", message = "Should be valid phone number")
    var operatorPhoneNo:String,
    @JsonIgnore
    var isLoggedIn:Boolean? = false,
    @field: Valid
    var operatorAddress:WarehouseOperatorAddress,
    var lastLogin: String? =""
)