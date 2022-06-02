package com.pestManage.pesticideMange.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class DeliverAddress(
    @field:NotNull(message = "Mandatory, Only digits accepted")
    var doorNo:Int,
    @field:NotBlank(message = "Mandatory")
    var place:String,
    @field:NotBlank(message = "Mandatory")
    var city:String,
    @field:NotBlank(message = "Mandatory")
    var state:String,
    @field: Pattern(regexp="^\$|[0-9]{6}", message = "Pincode should be exactly 6 digits and no white space allowed.")
    var pincode:String
)