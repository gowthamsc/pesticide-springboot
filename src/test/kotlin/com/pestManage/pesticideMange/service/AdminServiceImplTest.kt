package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.AdminDAO
import com.pestManage.pesticideMange.exception.AddException
import com.pestManage.pesticideMange.exception.DeleteException
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.Admin
import com.pestManage.pesticideMange.model.AdminTemp
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminServiceImplTest @Autowired constructor(
    var adminService: AdminService

){

    @Test
    fun addAdmin() {

        var adminTemp: AdminTemp = AdminTemp("adminTestService@gmail.com","181Cs158$","gowtham","9994501743",65,"nkl","nkl","tn","637210")
        var admin = this.adminService.addAdmin(adminTemp)
        assertThat(admin).isNotNull

        org.junit.jupiter.api.assertThrows<AddException>(message = "Already exists...") {
            this.adminService.addAdmin(adminTemp)
        }

        assertThat(this.adminService.viewAdmin(admin.email)).isNotNull

        assertThat(this.adminService.updateAdmin(admin.email,adminTemp)).isNotNull

        assertThat(this.adminService.deleteAdmin(admin.email)).isEqualTo("Deleted Sucessfully")
    }

    @Test
    fun `should get Not Found for invalid email for find`(){
        var exception =
            org.junit.jupiter.api.assertThrows<GetException>(message = "No admin found in this email id: a1@gmail.com") {
                this.adminService.viewAdmin("a1@gmail.com")
            }
    }

    @Test
    fun `should get not found for invalid email on updating`(){
        var adminTemp: AdminTemp = AdminTemp("a1@gmail.com","181Cs158$","gowtham","9994501743",65,"nkl","nkl","tn","637210")

        var exception =
            org.junit.jupiter.api.assertThrows<GetException>(message = "No admin found in this email") {
                this.adminService.updateAdmin("a1@gmail.com",adminTemp)
            }
    }

    @Test
    fun `should get not found for deleting`(){
        var exception =
            org.junit.jupiter.api.assertThrows<DeleteException>(message = "No admin found in this email id: a1@gmail.com") {
                this.adminService.deleteAdmin("a1@gmail.com")
            }
    }
}