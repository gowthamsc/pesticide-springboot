package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.AdminDAO
import com.pestManage.pesticideMange.exception.AddException
import com.pestManage.pesticideMange.exception.DeleteException
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.Admin
import com.pestManage.pesticideMange.model.AdminAddress
import com.pestManage.pesticideMange.model.AdminTemp
import org.springframework.stereotype.Service

@Service
class AdminServiceImpl(
    var adminDAO: AdminDAO
) : AdminService {
    override fun addAdmin(adminTemp: AdminTemp): Admin {
        if (this.adminDAO.existsByEmail(adminTemp.email))
            throw AddException("Already exists...")
        else {

            return this.adminDAO.save(
                Admin(
                    adminTemp.email,
                   adminTemp.password,
                    adminTemp.name,
                    adminTemp.phoneNo,
                    AdminAddress(adminTemp.doorNo, adminTemp.place, adminTemp.city, adminTemp.state, adminTemp.pincode)
                )
            )
        }
    }

    override fun viewAdmin(email: String): Admin {
        if (this.adminDAO.existsByEmail(email))
            return this.adminDAO.findById(email).get()
        else
            throw GetException("No admin found in this email id: " + email)
    }

    override fun deleteAdmin(email: String): String {
        if (this.adminDAO.existsByEmail(email)) {
            this.adminDAO.deleteById(email)
            return "Deleted Sucessfully"
        } else
            throw DeleteException("No admin found in this email id: " + email)
    }

    override fun updateAdmin(email: String, adminTemp: AdminTemp): Admin {
        if (adminDAO.existsByEmail(email)) {
            var admin: Admin = this.viewAdmin(email)
            admin.email = adminTemp.email
            admin.name = adminTemp.name
            admin.phoneNo = adminTemp.phoneNo
            admin.address!!.doorNo = adminTemp.doorNo
            admin.address!!.city = adminTemp.city
            admin.address!!.place = adminTemp.place
            admin.address!!.state = adminTemp.state
            admin.address!!.pincode = adminTemp.pincode
            adminDAO.save(admin)
            return admin
        } else {
            throw GetException("No admin found in this email")
        }
    }

    override fun chnagePassword(email: String, oldPassword: String, newPassword: String): String {
        if (this.adminDAO.existsByEmail(email)) {
            var admin: Admin = this.viewAdmin(email)
            if (admin.password.equals(oldPassword)) {
                admin.password = newPassword
                adminDAO.save(admin)
                return "Successfully changed"
            } else
                throw GetException("invalid old password!!!")
        } else
            throw GetException("No admin found in this email")
    }
}