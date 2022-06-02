package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.model.Delivery
import com.pestManage.pesticideMange.model.DeliveryTemp

interface DeliverPersonService {
    fun addDeliverPerson(delivery: Delivery): Delivery
    fun viewAllDeliverPerson(): List<Delivery>
    fun viewByEmail(email: String): Delivery
    fun updateDeliverPerson(email: String, deliveryTemp: DeliveryTemp): Delivery
    fun deleteDeliverPerson(email: String): String
    fun changePassword(email: String, oldPassword: String, newPassword: String): String
}