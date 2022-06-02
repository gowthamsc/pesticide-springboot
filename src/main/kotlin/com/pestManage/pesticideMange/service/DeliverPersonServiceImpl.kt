package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.*
import com.pestManage.pesticideMange.exception.AddException
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.Delivery
import com.pestManage.pesticideMange.model.DeliveryTemp
import org.springframework.stereotype.Service

@Service
class DeliverPersonServiceImpl(
    var farmerDAO: FarmerDAO,
    var consultantDAO: ConsultantDAO,
    var warehouseOperatorDAO: WarehouseOperatorDAO,
    var adminDAO: AdminDAO,
    var deliverPersonDAO: DeliverPersonDAO
) : DeliverPersonService {
    override fun addDeliverPerson(delivery: Delivery): Delivery {
        if (deliverPersonDAO.existsByEmail(delivery.email) || farmerDAO.existsByEmail(delivery.email) || consultantDAO.existsByConsultantEmail(
                delivery.email
            ) || warehouseOperatorDAO.existsByOperatorEmail(delivery.email) || adminDAO.existsByEmail(delivery.email)
        ) throw AddException("Email already exists")
        else {
            delivery.password = randomAlphanumericString()
            return this.deliverPersonDAO.save(delivery)
        }

    }

    fun randomAlphanumericString(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(8) { charset.random() }.joinToString("")
    }

    override fun viewAllDeliverPerson(): List<Delivery> {
        if (deliverPersonDAO.count() > 0) {
            return this.deliverPersonDAO.findAll()
        } else {
            throw GetException("Empty list")
        }
    }

    override fun viewByEmail(email: String): Delivery {
        if (this.deliverPersonDAO.existsByEmail(email)) return this.deliverPersonDAO.findById(email).get()
        else throw GetException("No delivery person found")
    }

    override fun updateDeliverPerson(email: String, deliveryTemp: DeliveryTemp): Delivery {
        if (this.deliverPersonDAO.existsByEmail(email)) {
            var deliverPerson = this.viewByEmail(email)
            deliverPerson.name = deliveryTemp.name
            deliverPerson.phoneNo = deliveryTemp.phoneNo
            deliverPerson.deliverAddress?.doorNo = deliveryTemp.doorNo
            deliverPerson.deliverAddress?.place = deliveryTemp.place
            deliverPerson.deliverAddress?.city = deliveryTemp.city
            deliverPerson.deliverAddress?.state = deliveryTemp.state
            deliverPerson.deliverAddress?.pincode = deliveryTemp.pincode
            return this.deliverPersonDAO.save(deliverPerson)

        } else throw GetException("No delivery person found")
    }

    override fun deleteDeliverPerson(email: String): String {
        if (this.deliverPersonDAO.existsByEmail(email)) {
            deliverPersonDAO.deleteById(email)
            return "Deleted Successfully"
        } else throw GetException("No delivery person found")
    }

    override fun changePassword(email: String, oldPassword: String, newPassword: String): String {
        if (this.deliverPersonDAO.existsByEmail(email)) {
            var deliverPerson = this.viewByEmail(email)
            if (deliverPerson.password.equals(oldPassword)) {
                deliverPerson.password = newPassword
                deliverPersonDAO.save(deliverPerson)
                return "Updated Successfully"

            } else {
                throw GetException("Invalid old Password")
            }

        } else throw GetException("No delivery person found")
    }
}