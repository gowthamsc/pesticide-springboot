package com.pestManage.pesticideMange.model

import org.bson.types.ObjectId

class TempPest (
    var objectid:String =ObjectId().toString(),
    var reqQty:Int
)