package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.model.OrderHistory
import com.pestManage.pesticideMange.model.ShipmentTemp

interface ShipmentService {
    fun deliveryStatus(shipmentTemp: ShipmentTemp): OrderHistory
    fun getAllOrderHistory(): List<OrderHistory>
}