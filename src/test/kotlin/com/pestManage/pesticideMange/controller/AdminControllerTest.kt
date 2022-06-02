package com.pestManage.pesticideMange.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.pestManage.pesticideMange.model.Admin
import com.pestManage.pesticideMange.model.AdminTemp
import com.pestManage.pesticideMange.service.AdminService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminControllerTest(
    @Autowired val mocMVC: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val adminService: AdminService
) {

    @Test
    fun addAdmin() {
        var admin: AdminTemp = AdminTemp("adminTestController@gmail.com","181Cs158$","gowtham","9994501743",65,"nkl","nkl","tn","637210")
        mocMVC.post("/admin"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(admin)
        }.andDo { print() }
            .andExpect { status{isOk()} }
        var ad = adminService.viewAdmin(admin.email)

        //duplicate entry
        mocMVC.post("/admin"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(admin)
        }.andDo { print() }
            .andExpect { status{isBadRequest()} }

        //get admin
        mocMVC.get("/admin/${admin.email}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //update admin
        var adminTemp: AdminTemp = AdminTemp("adminTestController@gmail.com","181Cs158$","gowtham","9994501743",65,"nkl","nkl","tn","637210")
        mocMVC.put("/admin/${admin.email}"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(adminTemp)
        }.andDo { print() }
            .andExpect { status{isOk()} }

        //change password
        mocMVC.put("/admin/changepassword/${admin.email}/${ad.password}/191Cs159$")
        .andDo { print() }
            .andExpect { status{isOk()} }

        //delete admin
        mocMVC.delete("/admin/${admin.email}")
            .andDo { print() }
            .andExpect { status{isOk()} }
    }

    @Test
    fun `should get Not Found for invalid admin email`(){
        mocMVC.get("/admin/adminTestController1@gmail.com")
            .andDo { print() }
            .andExpect { status{isBadRequest()} }
    }

    @Test
    fun `should get Not Found for inavlid email`(){
        var admin: AdminTemp = AdminTemp("admintestController1@gmail.com","181Cs158$","gowtham","9994501743",65,"nkl","nkl","tn","637210")
        mocMVC.put("/admin"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(admin)
        }
            .andDo { print() }
            .andExpect { status{isBadRequest()} }
    }

    @Test
    fun `should get Not Found for invalid email`(){
        mocMVC.delete("/admin/adminTestController1@gmail.com")
            .andDo { print() }
            .andExpect { status{isBadRequest()} }
    }
}