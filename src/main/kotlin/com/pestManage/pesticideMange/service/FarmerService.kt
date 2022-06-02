package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.model.Farmer
import com.pestManage.pesticideMange.model.FarmerTemp
import com.pestManage.pesticideMange.model.OrderHistory

interface FarmerService {
    fun addFarmer(farmer: Farmer): Farmer
    fun getFarmerById(email: String): Farmer
    fun getAllFarmer(): List<Farmer>
    fun updateFarmer(email: String, farmer: FarmerTemp): Farmer
    fun changePassword(email: String, oldPassword: String, newPassword: String): Farmer
    fun getOrderHistoryByDisease(id: String): OrderHistory
    fun getFarmerByDiseaseId(diseaseId: String): Farmer
    fun deleteById(email: String): String
    fun changePaymentAgree(id: String, payThrough: String, paymentID: String): OrderHistory
}