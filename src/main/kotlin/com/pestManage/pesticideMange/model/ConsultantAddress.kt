package com.pestManage.pesticideMange.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class ConsultantAddress(
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