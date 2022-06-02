package com.pestManage.pesticideMange.controller

import com.pestManage.pesticideMange.model.OrderHistory
import com.pestManage.pesticideMange.model.ShipmentTemp
import com.pestManage.pesticideMange.service.ShipmentService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class Shippment(var shipmentService: ShipmentService) {
    @PutMapping("/updateshipment")
    fun updateShipment(@RequestBody shipmentTemp: ShipmentTemp): OrderHistory {
        return this.shipmentService.deliveryStatus(shipmentTemp)
    }

    @GetMapping("/orderhistory")
    fun getAll(): List<OrderHistory> = this.shipmentService.getAllOrderHistory()
}