package com.pestManage.pesticideMange.service

interface CountService {
    fun diseaseCount():Long
    fun diseaseCountByFarmer(email:String):Long
    fun diseaseCountByConsultant(email:String):Long
    fun diseaseCountByConsultantAndReviewStatus(email:String,reviewStatus:String):Long
    fun countByReviewStatus(reviewStatus: String):Long
    fun countByFarmerAndReviewStatus(email:String,reviewStatus: String):Long
    fun consultantCount():Long
    fun warehouseOperatorCount():Long
    fun farmerCount():Long
    fun deliverPersonCount():Long
    fun shipmentCount(shipmentStatus:String):Long
    fun pesticideCount():Long
    fun pesticideOutOfStockCount():Long
    fun countDeliverListByMail(email:String):Long
    fun countDeliverpersonAndShipment(email:String,status:String):Long
}