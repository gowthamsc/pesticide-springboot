package com.pestManage.pesticideMange.model

import org.bson.types.ObjectId

class TempPestCart(
    //var farmerEmail:String,
    var consultantEmail:String,
    var diseaseId:String? = ObjectId().toString(),
    var suggestion:String?,
    var prescription:String?,
    var tempPest:MutableList<TempPest>? = mutableListOf()
)