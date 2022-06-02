package com.pestManage.pesticideMange.model


import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("orderHistory")
data class OrderHistory(
    @Id
    var id:String? = ObjectId().toString(),
    var farmerEmail:String,
    var disease: Disease?,
    var suggestion:String? ="***",
    var pesticides: MutableList<PesticideAddedToCart>? = mutableListOf<PesticideAddedToCart>(),
    var prescription:String? ="***",
    var count:Int =0,
    var totalPay:Double=0.0,
    var shipmentStatus:String? ="Waiting for review",
    var agree:String ="Nothing",
    var paid:String ="Pending",
    var deliveryStatus:String="Waiting for Delivery",
    var intimationCount:Int=0,
    var payThrough:String?,
    var paymentID:String?,
    var consultationFee:Double?,
    var deliverPersonPincode:String?,
    var deliverPersonEmail:String?,
    var reviewedDate: String?,
    var consultFeePaid:String? = "",
    var consultantFeePaidPaymentId:String?


)
{
    constructor(id:String,email:String,isAgree:String,isPaid:String) : this(id,email,null,"***",null,"***",0,0.0,"Waiting for review",isAgree,isPaid,"Waiting for Delivery",0,null,null,0.0,null,null,null, "",null)
}