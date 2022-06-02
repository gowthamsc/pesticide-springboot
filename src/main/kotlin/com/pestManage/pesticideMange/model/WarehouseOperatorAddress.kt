package com.pestManage.pesticideMange.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class WarehouseOperatorAddress(
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