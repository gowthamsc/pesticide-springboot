package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.model.WarehouseOperator
import com.pestManage.pesticideMange.model.WarehouseOperatorTemp

interface WarehouseOperatorService {
    fun addPesticideOperator(warehouseOperator: WarehouseOperator): WarehouseOperator
    fun updateWarehouseOperator(email: String, warehouseOperator: WarehouseOperatorTemp): WarehouseOperator
    fun changePassword(email: String, oldPassword: String, newPassword: String): WarehouseOperator
    fun viewWarehouseOperator(email: String): WarehouseOperator
    fun viewAllWarehouseOperator(): List<WarehouseOperator>
    fun deleteWarehouseOperator(email: String): String

}