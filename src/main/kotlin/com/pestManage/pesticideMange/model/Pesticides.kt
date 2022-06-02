package com.pestManage.pesticideMange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document("pesticide")
data class Pesticides(
    @Id
    var pestId:String? = ObjectId().toString(),
    @field:NotBlank(message = "Pesticide name can't be blank, it's mandatory")
    var pestName:String,
    @field:NotNull(message = "Mandatory, Only digits accepted")
    var avlQtyInGm:Int,
    @field:NotNull(message = "Mandatory, Only digits accepted")
    var pricePerGm:Double
)
