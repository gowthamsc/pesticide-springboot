package com.pestManage.pesticideMange.DAO

import com.pestManage.pesticideMange.model.Delivery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliverPersonDAO : MongoRepository<Delivery, String> {
    fun existsByEmail(email: String): Boolean
    fun existsByEmailAndPassword(email: String, password: String): Boolean

    fun findByDeliverAddressPincode(pincode:String):List<Delivery>
}