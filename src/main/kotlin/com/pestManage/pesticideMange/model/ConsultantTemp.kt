package com.pestManage.pesticideMange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class ConsultantTemp (
    @field:Email(message = "Email should be valid")
    var consultantEmail:String,
    @field:NotBlank(message = "Name can't be blank, it's mandatory")
    var consultantName:String,
    @field: Pattern(regexp="^\$|[0-9]{10}", message = "Should be valid phone number")
    var consultantPhoneNo:String,
    @field:NotNull(message = "Mandatory, Only digits accepted")
    var consultantDoorNo:Int,
    @field:NotBlank(message = "Mandatory")
    var consultantPlace:String,
    @field:NotBlank(message = "Mandatory")
    var consultantCity:String,
    @field:NotBlank(message = "Mandatory")
    var consultantState:String,
    @field: Pattern(regexp="^\$|[0-9]{6}", message = "Pincode should be exactly 6 digits and no white space allowed.")
    var consultantPincode:String
        )