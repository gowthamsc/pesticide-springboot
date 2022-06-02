package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.*
import com.pestManage.pesticideMange.exception.*
import com.pestManage.pesticideMange.model.Delivery
import com.pestManage.pesticideMange.model.Farmer
import com.pestManage.pesticideMange.model.FarmerTemp
import com.pestManage.pesticideMange.model.OrderHistory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class FarmerServiceImpl(
    var farmerDao: FarmerDAO,
    var diseaseDAO: DiseaseDAO,
    var sendEmailServiceImpl: SendEmailServiceImpl,
    var consultantDAO: ConsultantDAO,
    var warehouseOperatorDAO: WarehouseOperatorDAO,
    var orderDAO: OrderDAO,
    var deliverPersonService: DeliverPersonService,
    var deliverPersonDAO: DeliverPersonDAO
) : FarmerService {

    override fun addFarmer(farmer: Farmer): Farmer {
        if (farmerDao.existsByEmail(farmer.email) || consultantDAO.existsByConsultantEmail(farmer.email) || warehouseOperatorDAO.existsByOperatorEmail(
                farmer.email
            )
        ) {
            throw AddException("Email already exists")
        } else {
//            val passwordEncoder = BCryptPasswordEncoder()
//            farmer.password = passwordEncoder.encode(randomAlphanumericString())
            farmer.password = randomAlphanumericString()
            var subject: String = "Welcome to Pesticide Management Service"
            var body: String =
                "Hi ${farmer.name},\n Your pesticide management account created successfully. You can now login and get solution for your crops.\n Your details are as follows: \nName: ${farmer.name}\nEmail: ${farmer.email}\nPassword: ${farmer.password}\nPhone Number: ${farmer.phoneNo}.\n\n Thanks,\n Pesticide Management Team"

            sendEmailServiceImpl.sendMail(farmer.email, body, subject)
            return this.farmerDao.insert(farmer)
        }
    }


    fun randomAlphanumericString(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(8) { charset.random() }.joinToString("")
    }


    override fun getFarmerById(email: String): Farmer {

        if (farmerDao.existsByEmail(email)) return this.farmerDao.findById(email).get()
        else throw GetException("Farmer not found, Please register....")
    }

    override fun getAllFarmer(): List<Farmer> {
        if (farmerDao.count() > 0) return this.farmerDao.findAll()
        else throw GetException("No farmers found, Please register....")
    }

    override fun updateFarmer(email: String, farmer: FarmerTemp): Farmer {
        if (farmerDao.existsByEmail(email)) {
            var farmerTemp = this.getFarmerById(email)
            farmerTemp.email = farmer.email
            farmerTemp.name = farmer.name
            farmerTemp.phoneNo = farmer.phoneNo
            farmerTemp.address!!.doorNo = farmer.doorNo
            farmerTemp.address!!.place = farmer.place
            farmerTemp.address!!.city = farmer.city
            farmerTemp.address!!.state = farmer.state
            farmerTemp.address!!.pincode = farmer.pincode
            return farmerDao.save(farmerTemp)
        } else throw GetException("Farmer not found, Please register....")
    }

    override fun changePassword(email: String, oldPassword: String, newPassword: String): Farmer {
        var farmer = this.getFarmerById(email)
        if (farmer.isLoggedIn!!.equals(true)) {
            if (farmer.password.equals(oldPassword)) {
                farmer.password = newPassword
                farmerDao.save(farmer)
                return farmer
            } else throw GetException("invalid old password!!!")
        } else throw UnauthorizedException("Please login")
    }

    override fun getOrderHistoryByDisease(id: String): OrderHistory {
        return orderDAO.findById(id).get()
    }

    override fun getFarmerByDiseaseId(diseaseId: String): Farmer {
        var disease = this.diseaseDAO.findById(diseaseId).get()
        return this.getFarmerById(disease.email!!)
    }

    override fun deleteById(email: String): String {
        if (farmerDao.existsByEmail(email)) {
            this.farmerDao.deleteById(email)
            this.orderDAO.deleteByFarmerEmail(email)
            return "Deleted successfully"
        } else {
            throw UnauthorizedException("Please login")
        }
    }

    override fun changePaymentAgree(id: String, payThrough: String, paymentID: String): OrderHistory {
        var disease = this.diseaseDAO.findById(id).get()
        var order = this.orderDAO.findById(disease.dieaseId!!).get()
        var farmer: Farmer = this.getFarmerById(disease.email!!)
        if (order.agree.equals("Pending")) {
                if(payThrough.equals("Online Payment")) {
                    var pincode = deliverPersonService.viewAllDeliverPerson()
                    var pincode_List = mutableListOf<Int>()
                    for (pin in pincode) {
                        pincode_List.add(pin.deliverAddress!!.pincode.toInt())
                    }
                    var intArray = pincode_List.toIntArray()
                    var farmer_Pincode = farmer.address!!.pincode.toInt()
                    var nearest_Pincode = findClosest(intArray, farmer_Pincode).toString()
                    var deliver: List<Delivery> = deliverPersonDAO.findByDeliverAddressPincode(nearest_Pincode)
                    val randomizer = Random()
                    val delivery_Temp: Delivery = deliver.get(randomizer.nextInt(deliver.size))
                    order.deliverPersonPincode = delivery_Temp.deliverAddress!!.pincode
                    order.deliverPersonEmail = delivery_Temp.email
                    var subject: String = "Pesticide Management System"
                    var body: String ="Hi, \nYour account got order. Please check your account login.\n\n Thanks,\n Pesticide Management Team"
                    sendEmailServiceImpl.sendMail(order.deliverPersonEmail!!, body, subject)
                    order.shipmentStatus = "Your order has been placed successfully and will be delivered shortly."
                    order.agree = "Approved"
                    order.paid = "Paid"
                    order.payThrough = payThrough
                    order.paymentID = paymentID
                    order.consultFeePaid = "Paid"
                    orderDAO.save(order)
                    farmer.orderHistory!!.filter { it == order }.map { o -> o.shipmentStatus = order.shipmentStatus }
                    farmer.orderHistory!!.filter { it == order }.map { o -> o.paid = "Paid" }
                    farmer.orderHistory!!.filter { it == order }.map { o -> o.deliverPersonPincode = nearest_Pincode }
                    farmer.orderHistory!!.filter { it == order }.map { o -> o.agree = "Approved" }
                    farmer.orderHistory!!.filter { it == order }.map { o -> o.payThrough = payThrough }
                    farmer.orderHistory!!.filter { it == order }.map { o -> o.paymentID = paymentID }
                    farmerDao.save(farmer)
                    return order
                }
                else{
                    order.shipmentStatus = "Thank you for using pesticide management service"
                    order.agree = "Declined"
                    order.paid = "Not Paid"
                    order.consultFeePaid = "Paid"
                    order.consultantFeePaidPaymentId =paymentID
                    orderDAO.save(order)
                    farmer.orderHistory!!.filter { it == order }.map { o -> o.shipmentStatus = order.shipmentStatus }
                    farmer.orderHistory!!.filter { it == order }.map { o -> o.paid = order.paid}
                    farmer.orderHistory!!.filter { it == order }.map { o -> o.agree =   order.agree }
                    farmerDao.save(farmer)
                    return order
                }


        } else {
            throw GetException("There\'s no pesticides in your cart")
        }
    }


    fun findClosest(arr: IntArray, target: Int): Int {
        arr.sort()
        val n = arr.size
        if (target <= arr[0]) return arr[0]
        if (target >= arr[n - 1]) return arr[n - 1]
        var i = 0
        var j = n
        var mid = 0
        while (i < j) {
            mid = (i + j) / 2
            if (arr[mid] == target) return arr[mid]
            if (target < arr[mid]) {
                if (mid > 0 && target > arr[mid - 1]) return getClosest(arr[mid - 1], arr[mid], target)
                j = mid
            } else {
                if (mid < n - 1 && target < arr[mid + 1]) return getClosest(arr[mid], arr[mid + 1], target)
                i = mid + 1
            }
        }
        return arr[mid]
    }

    fun getClosest(val1: Int, val2: Int, target: Int): Int {
        return if (target - val1 >= val2 - target) val2 else val1
    }
}