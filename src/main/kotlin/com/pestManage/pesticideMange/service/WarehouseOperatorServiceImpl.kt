package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.ConsultantDAO
import com.pestManage.pesticideMange.DAO.FarmerDAO
import com.pestManage.pesticideMange.DAO.WarehouseOperatorDAO
import com.pestManage.pesticideMange.exception.AddException
import com.pestManage.pesticideMange.exception.DeleteException
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.WarehouseOperator
import com.pestManage.pesticideMange.model.WarehouseOperatorTemp
import org.springframework.stereotype.Service

@Service
class WarehouseOperatorServiceImpl(
    var warehouseOperatorDAO: WarehouseOperatorDAO,
    var sendEmailServiceImpl: SendEmailServiceImpl,
    var farmerDAO: FarmerDAO,
    var consultantDAO: ConsultantDAO
) : WarehouseOperatorService {
    override fun addPesticideOperator(warehouseOperator: WarehouseOperator): WarehouseOperator {
        if (warehouseOperatorDAO.existsByOperatorEmail(warehouseOperator.operatorEmail) || consultantDAO.existsByConsultantEmail(
                warehouseOperator.operatorEmail
            ) || farmerDAO.existsByEmail(warehouseOperator.operatorEmail)
        ) throw AddException("Email already exists")
        else {
            warehouseOperator.operatorPassword = randomAlphanumericString()
            var subject: String = "Welcome To Pesticide Management Service"
            var body: String =
                "Hi ${warehouseOperator.operatorName},\n Congratulations!!!. You are registered as warehouse Operator successfully. Your details are as follows: \nName: ${warehouseOperator.operatorName}\nEmail: ${warehouseOperator.operatorEmail}\nPassword: ${warehouseOperator.operatorPassword} (Don\'t disclose your password to anyone)\nPhone Number: ${warehouseOperator.operatorPhoneNo}.\n\n Thanks,\n Pesticide Management Team"
            sendEmailServiceImpl.sendMail(warehouseOperator.operatorEmail, body, subject)
            return this.warehouseOperatorDAO.save(warehouseOperator)
        }
    }

    fun randomAlphanumericString(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(8) { charset.random() }.joinToString("")
    }

    override fun updateWarehouseOperator(email: String, warehouseOperator: WarehouseOperatorTemp): WarehouseOperator {
        if (warehouseOperatorDAO.existsByOperatorEmail(email)) {
            var warehouseOperatorTemp = this.warehouseOperatorDAO.findById(email).get()
            warehouseOperatorTemp.operatorEmail = warehouseOperator.operatorEmail
            warehouseOperatorTemp.operatorName = warehouseOperator.operatorName
            warehouseOperatorTemp.operatorPhoneNo = warehouseOperator.operatorPhoneNo
            warehouseOperatorTemp.operatorAddress.operatorDoorNo = warehouseOperator.operatorDoorNo
            warehouseOperatorTemp.operatorAddress.operatorPlace = warehouseOperator.operatorPlace
            warehouseOperatorTemp.operatorAddress.operatorCity = warehouseOperator.operatorCity
            warehouseOperatorTemp.operatorAddress.operatorState = warehouseOperator.operatorState
            warehouseOperatorTemp.operatorAddress.operatorPincode = warehouseOperator.operatorPincode
            return this.warehouseOperatorDAO.save(warehouseOperatorTemp)
        } else throw GetException("Not found")
    }

    override fun changePassword(email: String, oldPassword: String, newPassword: String): WarehouseOperator {
        if (warehouseOperatorDAO.existsByOperatorEmail(email)) {
            var warehouseOperator = this.warehouseOperatorDAO.findById(email).get()
            if (warehouseOperator.operatorPassword.equals(oldPassword)) {
                warehouseOperator.operatorPassword = newPassword
                return this.warehouseOperatorDAO.save(warehouseOperator)
            } else throw GetException("invalid old password!!!")

        } else throw GetException("Not found")
    }

    override fun viewWarehouseOperator(email: String): WarehouseOperator {
        if (warehouseOperatorDAO.existsByOperatorEmail(email)) {
            return this.warehouseOperatorDAO.findById(email).get()
        } else throw GetException("Not found")
    }

    override fun viewAllWarehouseOperator(): List<WarehouseOperator> {
        if (warehouseOperatorDAO.count() > 0) return this.warehouseOperatorDAO.findAll()
        else throw GetException("Not found")
    }

    override fun deleteWarehouseOperator(email: String): String {
        if (warehouseOperatorDAO.existsByOperatorEmail(email)) {
            this.warehouseOperatorDAO.deleteById(email)
            return "Deleted Successfully"
        } else throw DeleteException("Not found")
    }
}