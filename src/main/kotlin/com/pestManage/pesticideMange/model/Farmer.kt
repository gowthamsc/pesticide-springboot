package com.pestManage.pesticideMange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.*
import javax.validation.executable.ValidateOnExecution

@Document("farmer")
@ValidateOnExecution
data class Farmer(
    @Id
    @field:Email(message = "Email should be valid")
    var email:String,
    @JsonIgnore
    var type:String? = "Farmer",
   var password:String?,
    @field:NotBlank(message = "Name can't be blank, it's mandatory")
    var name:String,
    @field: Pattern(regexp="^\$|[0-9]{10}", message = "Should be valid phone number")
    var phoneNo:String,
    @field: Valid
    var address:Address?,
    @Transient
    @JsonIgnore
    var isLoggedIn:Boolean? = false,
    var diseease: MutableList<Disease>? = mutableListOf<Disease>(),

    var orderHistory:MutableList<OrderHistory>? = mutableListOf<OrderHistory>(),
    var lastLogin: String? =""


) {

 constructor(email:String,
              name:String,
              phoneNo:String,
              address:Address):this(email,"Farmer",null,name,phoneNo,address,null,null,null,"")
}
