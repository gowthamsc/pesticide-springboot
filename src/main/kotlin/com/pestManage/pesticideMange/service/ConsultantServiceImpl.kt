package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.ConsultantDAO
import com.pestManage.pesticideMange.DAO.FarmerDAO
import com.pestManage.pesticideMange.DAO.WarehouseOperatorDAO
import com.pestManage.pesticideMange.exception.AddException
import com.pestManage.pesticideMange.exception.DeleteException
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.Consultant
import com.pestManage.pesticideMange.model.ConsultantTemp
import org.springframework.stereotype.Service

@Service
class ConsultantServiceImpl(
    var consultanDAO: ConsultantDAO,
    var sendEmailServiceImpl: SendEmailServiceImpl,
    var farmerDAO: FarmerDAO,
    var warehouseOperatorDAO: WarehouseOperatorDAO
) : ConsultantService {
    override fun addConsultant(consultant: Consultant): Consultant {
        if (consultanDAO.existsByConsultantEmail(consultant.consultantEmail) || farmerDAO.existsByEmail(consultant.consultantEmail) || warehouseOperatorDAO.existsByOperatorEmail(
                consultant.consultantEmail
            )
        ) throw AddException("Email already exists")
        else {
            consultant.consultantPassword = randomAlphanumericString()
            var subject: String = "Welcome To Pesticide Management Service"
            var body: String =
                "Hi ${consultant.consultantName},\n Congratulations!!!. You are registered as consultant successfully. Your details are as follows: \nName: ${consultant.consultantName}\nEmail: ${consultant.consultantEmail}\nPassword: ${consultant.consultantPassword} (Don\'t disclose your password to anyone)\nPhone Number: ${consultant.consultantPhoneNo}.\n\n Thanks,\n Pesticide Management Team"
            sendEmailServiceImpl.sendMail(consultant.consultantEmail, body, subject)
            return this.consultanDAO.save(consultant)
        }
    }

    fun randomAlphanumericString(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(8) { charset.random() }.joinToString("")
    }

    override fun viewConsultant(email: String): Consultant {
        if (consultanDAO.existsByConsultantEmail(email)) return this.consultanDAO.findById(email).get()
        else throw GetException("No such consultant found")
    }

    override fun updateConsultant(email: String, consultant: ConsultantTemp): Consultant {
        if (consultanDAO.existsByConsultantEmail(email)) {
            var consultantTemp = this.consultanDAO.findById(email).get()
            consultantTemp.consultantName = consultant.consultantName
            consultantTemp.consultantPhoneNo = consultant.consultantPhoneNo
            consultantTemp.consultantEmail = consultant.consultantEmail
            consultantTemp.consultantAddress.consultantDoorNo = consultant.consultantDoorNo
            consultantTemp.consultantAddress.consultantPlace = consultant.consultantPlace
            consultantTemp.consultantAddress.consultantCity = consultant.consultantCity
            consultantTemp.consultantAddress.consultantState = consultant.consultantState
            consultantTemp.consultantAddress.consultantPincode = consultant.consultantPincode
            consultanDAO.save(consultantTemp)
            return this.consultanDAO.findById(email).get()
        } else throw GetException("No such consultant found")
    }


    override fun changePassword(email: String, oldPassword: String, newPassword: String): Consultant {
        if (consultanDAO.existsByConsultantEmail(email)) {
            var consultant = this.consultanDAO.findById(email).get()
            if (consultant.consultantPassword.equals(oldPassword)) {
                consultant.consultantPassword = newPassword
                return consultanDAO.save(consultant)
            } else throw GetException("invalid old password!!!")
        } else throw GetException("No such consultant found")
    }

    override fun viewAllConsultant(): MutableList<Consultant> {
        if (consultanDAO.count() > 0) return this.consultanDAO.findAll()
        else throw GetException("No consultant available")
    }

    override fun deleteConsultant(email: String): String {
        if (consultanDAO.existsByConsultantEmail(email)) {
            this.consultanDAO.deleteById(email)
            return "Deleted Successfully"
        } else throw DeleteException("No such consultant found")
    }
}