package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.*
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.Admin
import com.pestManage.pesticideMange.model.Consultant
import com.pestManage.pesticideMange.model.Farmer
import com.pestManage.pesticideMange.model.WarehouseOperator
import com.pestManage.pesticideMange.util.JwtUtils
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.servlet.http.HttpServletResponse


@Service
class LoginServiceImpl(
    var farmerDAO: FarmerDAO,
    var consultantDAO: ConsultantDAO,
    var adminDAO: AdminDAO,
    var warehouseOperatorDAO: WarehouseOperatorDAO,
    var adminService: AdminService,
    var farmerService: FarmerService,
    var consultantService: ConsultantService,
    var warehouseOperatorService: WarehouseOperatorService,
    var deliverPersonService: DeliverPersonService,
    var deliverPersonDAO: DeliverPersonDAO,
    var sendEmailServiceImpl: SendEmailServiceImpl,
    var utils: JwtUtils
) : LoginService {
    override fun login(email: String, password: String, response: HttpServletResponse): Any {
      //  val passwordEncoder = BCryptPasswordEncoder()
        if (adminDAO.existsByEmailAndPassword(email,password)) {
            var admin: Admin = this.adminService.viewAdmin(email)
                admin.isLoggedIn = true
                adminDAO.save(admin)

            val jwt = utils.generateJwt(admin.email,admin.type!!, response)
                var loginList: Any = mutableListOf(admin.type, admin.isLoggedIn,jwt)
                return loginList

        } else if (farmerDAO.existsByEmailAndPassword(email, password)) {
            var farmer: Farmer = this.farmerService.getFarmerById(email)
            farmer.isLoggedIn = true
            farmerDAO.save(farmer)

            val jwt = utils.generateJwt(farmer.email,farmer.type!!, response)
            var loginList: Any = mutableListOf(farmer.type, farmer.isLoggedIn, jwt)
            return loginList

        } else if (consultantDAO.existsByConsultantEmailAndConsultantPassword(email, password)) {
            var consultant: Consultant = this.consultantService.viewConsultant(email)
            consultant.isLoggedIn = true
            consultantDAO.save(consultant)

            val jwt = utils.generateJwt(consultant.consultantEmail,consultant.type!!, response)
            var loginList: Any = mutableListOf(consultant.type, consultant.isLoggedIn,jwt)
            return loginList

        } else if (warehouseOperatorDAO.existsByOperatorEmailAndOperatorPassword(email, password)) {
            var warehouseOperator: WarehouseOperator = this.warehouseOperatorService.viewWarehouseOperator(email)
            warehouseOperator.isLoggedIn = true
            warehouseOperatorDAO.save(warehouseOperator)

            val jwt = utils.generateJwt(warehouseOperator.operatorEmail,warehouseOperator.type!!, response)
            var loginList: Any = mutableListOf(warehouseOperator.type, warehouseOperator.isLoggedIn,jwt)
            return loginList

        } else if (deliverPersonDAO.existsByEmailAndPassword(email, password)) {
            var delivery = this.deliverPersonService.viewByEmail(email)
            delivery.isLoggedIn = true
            deliverPersonDAO.save(delivery)

            val jwt = utils.generateJwt(delivery.email,delivery.type!!, response)
            var loginList: Any = mutableListOf(delivery.type, delivery.isLoggedIn,jwt)
            return loginList
        } else throw GetException("Email and password Not Found")
    }


    override fun forgetPassword(email: String): String {
        if (adminDAO.existsByEmail(email)) {
            var admin: Admin = this.adminService.viewAdmin(email)
            var subject: String = "Welcome to Pesticide Management Service"
            var body: String =
                "Hi Admin,\n Your request for forget password has been recieved and change your password by after login.  \nYour password details are: \nEmail: ${admin.email}\nPassword: ${admin.password}.\n\n Thanks,\n Pesticide Management Team"
            sendEmailServiceImpl.sendMail(admin.email, body, subject)
            return "Password successfully sent to your registered email."
        } else if (farmerDAO.existsByEmail(email)) {
            var farmer: Farmer = this.farmerService.getFarmerById(email)
            var subject: String = "Welcome to Pesticide Management Service"
            var body: String =
                "Hi ${farmer.name},\n Your request for forget password has been recieved and change your password by after login.  \nYour password details are: \nName: ${farmer.name}\nEmail: ${farmer.email}\nPassword: ${farmer.password}.\n\n Thanks,\n Pesticide Management Team"
            sendEmailServiceImpl.sendMail(farmer.email, body, subject)
            return "Password successfully sent to your registered email."
        } else if (consultantDAO.existsByConsultantEmail(email)) {
            var consultant = this.consultantService.viewConsultant(email)
            var subject: String = "Welcome to Pesticide Management Service"
            var body: String =
                "Hi ${consultant.consultantName},\n Your request for forget password has been recieved and change your password by after login.  \nYour password details are: \nName: ${consultant.consultantName}\nEmail: ${consultant.consultantEmail}\nPassword: ${consultant.consultantPassword}.\n\n Thanks,\n Pesticide Management Team"
            sendEmailServiceImpl.sendMail(consultant.consultantEmail, body, subject)
            return "Password successfully sent to your registered email."

        } else if (warehouseOperatorDAO.existsByOperatorEmail(email)) {
            var warehouseOperator = this.warehouseOperatorService.viewWarehouseOperator(email)
            var subject: String = "Welcome to Pesticide Management Service"
            var body: String =
                "Hi ${warehouseOperator.operatorName},\n Your request for forget password has been recieved and change your password by after login.  \nYour password details are: \nName: ${warehouseOperator.operatorName}\nEmail: ${warehouseOperator.operatorEmail}\nPassword: ${warehouseOperator.operatorPassword}.\n\n Thanks,\n Pesticide Management Team"
            sendEmailServiceImpl.sendMail(warehouseOperator.operatorEmail, body, subject)
            return "Password successfully sent to your registered email."
        }
        else if (deliverPersonDAO.existsByEmail(email)) {
            var delivery = this.deliverPersonService.viewByEmail(email)
            var subject: String = "Welcome to Pesticide Management Service"
            var body: String =
                "Hi ${delivery.name},\n Your request for forget password has been recieved and change your password by after login.  \nYour password details are: \nName: ${delivery.name}\nEmail: ${delivery.email}\nPassword: ${delivery.password}.\n\n Thanks,\n Pesticide Management Team"
            sendEmailServiceImpl.sendMail(delivery.email, body, subject)
            return "Password successfully sent to your registered email."
        }
        else {
            throw GetException("Invalid Email")
        }

    }


    override fun logout(email: String): String {
        if (adminDAO.existsByEmail(email)) {
            var admin: Admin = this.adminService.viewAdmin(email)
            admin.isLoggedIn = false
            val now = Date()
            val dtf = SimpleDateFormat("dd-MM-yyyy hh:mm")
            dtf.timeZone=TimeZone.getTimeZone("Asia/Calcutta")
            admin.lastLogin = dtf.format(now)
            adminDAO.save(admin)
            return "Logout successful"
        } else if (farmerDAO.existsByEmail(email)) {
            var farmer: Farmer = this.farmerService.getFarmerById(email)
            farmer.isLoggedIn = false
            val now = Date()
            val dtf = SimpleDateFormat("dd-MM-yyyy hh:mm")
            dtf.timeZone=TimeZone.getTimeZone("Asia/Calcutta")
            farmer.lastLogin = dtf.format(now)
            farmerDAO.save(farmer)
            return "Logout successful"

        } else if (consultantDAO.existsByConsultantEmail(email)) {
            var consultant: Consultant = this.consultantService.viewConsultant(email)
            consultant.isLoggedIn = false
            val now = Date()
            val dtf = SimpleDateFormat("dd-MM-yyyy hh:mm")
            dtf.timeZone=TimeZone.getTimeZone("Asia/Calcutta")
            consultant.lastLogin = dtf.format(now)
            consultantDAO.save(consultant)
            return "Logout successful"

        } else if (warehouseOperatorDAO.existsByOperatorEmail(email)) {
            var warehouseOperator: WarehouseOperator = this.warehouseOperatorService.viewWarehouseOperator(email)
            warehouseOperator.isLoggedIn = false
            val now = Date()
            val dtf = SimpleDateFormat("dd-MM-yyyy hh:mm")
            dtf.timeZone=TimeZone.getTimeZone("Asia/Calcutta")
            warehouseOperator.lastLogin = dtf.format(now)
            warehouseOperatorDAO.save(warehouseOperator)
            return "Logout successful"

        }else if (deliverPersonDAO.existsByEmail(email)) {
            var delivery = this.deliverPersonService.viewByEmail(email)
            delivery.isLoggedIn = false
            val now = Date()
            val dtf = SimpleDateFormat("dd-MM-yyyy hh:mm")
            dtf.timeZone=TimeZone.getTimeZone("Asia/Calcutta")
            delivery.lastLogin = dtf.format(now)
            deliverPersonDAO.save(delivery)
            return "Logout successful"

        }
        else throw GetException("Inavlid email")
    }
}