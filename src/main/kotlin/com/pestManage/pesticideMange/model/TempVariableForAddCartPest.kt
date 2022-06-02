package com.pestManage.pesticideMange.model

import org.bson.types.ObjectId


class TempVariableForAddCartPest (
    //var farmerEmail:String,
    var consultantEmail:String,
    var diseaseId:String? =ObjectId().toString()
)