package com.pestManage.pesticideMange.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("pesticideAddedToCart")
data class PesticideAddedToCart(
    @Id
    var id:String? =ObjectId().toString(),
    var pesticideName:String?,
    var pesticideQtyReq:Int?,
    var pesticidePricePerUnit:Double?
) {
    constructor() : this(null,null,0,0.0)
}
