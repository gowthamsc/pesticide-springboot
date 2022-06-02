package com.pestManage.pesticideMange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class WarehouseOperatorTemp (
    @field:Email(message = "Email should be valid")
    var operatorEmail:String,
    @field:NotBlank(message = "Name can't be blank, it's mandatory")
    var operatorName:String,
    @field: Pattern(regexp="^\$|[0-9]{10}", message = "Should be valid phone number")
    var operatorPhoneNo:String,
    @field:NotNull(message = "Mandatory, Only digits accepted")
    var operatorDoorNo:Int,
    @field:NotBlank(message = "Operator Place Mandatory")
    var operatorPlace:String,
    @field:NotBlank(message = "operator city Mandatory")
    var operatorCity:String,
    @field:NotBlank(message = "operator state Mandatory")
    var operatorState:String,
    @field: Pattern(regexp="^\$|[0-9]{6}", message = "Pincode should be exactly 6 digits and no white space allowed.")
    var operatorPincode:String

        )