package com.pestManage.pesticideMange.DAO

import com.pestManage.pesticideMange.model.OrderHistory
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderDAO : MongoRepository<OrderHistory, String> {
    fun findByFarmerEmail(email: String): MutableList<OrderHistory>
    fun findByDisease(diseaseId: String): OrderHistory

    fun countByShipmentStatus(deliveryStatus:String):Long
    fun countByDeliverPersonEmail(email:String):Long

    fun countByDeliverPersonEmailAndShipmentStatus(email:String, shipmentStatus:String):Long
    fun deleteByFarmerEmail(email: String)
}