package com.pestManage.pesticideMange.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document("disease")
data class Disease(
    @Id
    var dieaseId:String? = ObjectId().toString(),
    @field:Email(message = "Email should be valid")
    var email:String?,
    @field:NotBlank(message = "Disease Description can't be blank, it's mandatory")
    var diseaseDescription:String?,
    @field:NotNull(message = "Mandatory, Only digits accepted")
    var acre:Int?,
    var addedDate: String?,
    var reviewStatus:String?,
    var consultantName:String?,
    var consultantPhoneNo:String?,
    var consultantEmail:String?,
    var suggestion:String?,
    @field:NotNull(message = "Mandatory")
    var image:String?,
    @DBRef
    var pesticide:MutableList<PesticideAddedToCart>?= mutableListOf<PesticideAddedToCart>(),

){
    constructor() : this(null,null,null,null,null,null,null,null,null,null,null,null)
   constructor(email:String):this(null,email,null,0,null,null,null,null,null,null,null,null)
}