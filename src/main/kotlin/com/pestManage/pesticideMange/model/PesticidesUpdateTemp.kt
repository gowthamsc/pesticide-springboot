package com.pestManage.pesticideMange.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PesticidesUpdateTemp(
    @field:NotBlank(message = "Pesticide name can't be blank, it's mandatory")
    var pestName:String,
    @field:NotNull(message = "Mandatory, Only digits accepted")
    var avlQtyInGm:Int,
    @field:NotNull(message = "Mandatory, Only digits accepted")
    var pricePerGm:Double
)