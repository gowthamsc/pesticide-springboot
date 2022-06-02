package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.FarmerDAO
import com.pestManage.pesticideMange.DAO.OrderDAO
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.Farmer
import com.pestManage.pesticideMange.model.OrderHistory
import com.pestManage.pesticideMange.model.ShipmentTemp
import org.springframework.stereotype.Service

@Service
class ShipmentServiceImpl(
    var diseaseService: DiseaseService,
    var orderDAO: OrderDAO,
    var farmerDAO: FarmerDAO,
    var farmerService: FarmerService,
) : ShipmentService {

    override fun deliveryStatus(shipmentTemp: ShipmentTemp): OrderHistory {
        var disease = this.diseaseService.viewDiseaseByDiseaseId(shipmentTemp.id)
        var order = this.orderDAO.findById(disease.dieaseId!!).get()
        var farmer: Farmer = this.farmerService.getFarmerById(disease.email!!)
        if (order.agree.equals("Approved")) {
            if (order.payThrough.equals("COD")) {
                order.shipmentStatus = shipmentTemp.status
                order.paid = shipmentTemp.paidStatus
                if (shipmentTemp.status.equals("Intimation")) {
                    order.intimationCount = order.intimationCount.plus(1)
                    order.deliveryStatus = shipmentTemp.status + " Day:" + order.intimationCount.toString()
                } else {
                    order.deliveryStatus = shipmentTemp.status
                }
                orderDAO.save(order)
                farmer.orderHistory!!.filter { it == order }.map { d -> d.shipmentStatus = shipmentTemp.status }
                farmer.orderHistory!!.filter { it == order }.map { d -> d.paid = shipmentTemp.paidStatus }
                farmerDAO.save(farmer)
                return order
            } else {
                order.shipmentStatus = shipmentTemp.status
                if (shipmentTemp.status.equals("Intimation")) {
                    order.intimationCount = order.intimationCount.plus(1)
                    order.deliveryStatus = shipmentTemp.status + "\nDay: " + order.intimationCount.toString()
                } else {
                    order.deliveryStatus = shipmentTemp.status
                }
                orderDAO.save(order)
                farmer.orderHistory!!.filter { it == order }.map { d -> d.shipmentStatus = shipmentTemp.status }
                farmerDAO.save(farmer)
                return order
            }
        } else {
            throw GetException("Farmer should agree the payment to proceed")
        }
    }

    override fun getAllOrderHistory(): List<OrderHistory> {
        if (orderDAO.count() > 0) {
            return this.orderDAO.findAll()
        } else {
            throw GetException("Empty List")
        }
    }
}