package com.pestManage.pesticideMange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class DeliveryTemp(

    @field:Email(message = "Email should be valid")
    var email:String,
    @field:NotBlank(message = "Name can't be blank, it's mandatory")
    var name:String,
    @field: Pattern(regexp="^\$|[0-9]{10}", message = "Should be valid phone number")
    var phoneNo:String,
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
