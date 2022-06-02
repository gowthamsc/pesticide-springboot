package com.pestManage.pesticideMange.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class CountControllerTest(
    @Autowired val mocMVC: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @Test
    fun diseaseCount() {
        //get disease count by farmer
        mocMVC.get("/diseasecount")
            .andDo { print() }
            .andExpect { status{isOk()} }
    }

    @Test
    fun consultantCount() {
        mocMVC.get("/consultantcount")
            .andDo { print() }
            .andExpect { status{isOk()} }
    }

    @Test
    fun warehouseOperatorCount() {
        mocMVC.get("/warehouseoperatorcount")
            .andDo { print() }
            .andExpect { status{isOk()} }
    }

    @Test
    fun farmerCount() {
        mocMVC.get("/farmercount")
            .andDo { print() }
            .andExpect { status{isOk()} }
    }

    @Test
    fun deliverPersonCount() {
        mocMVC.get("/deliverpersoncount")
            .andDo { print() }
            .andExpect { status{isOk()} }
    }

    @Test
    fun pesticideCount() {
        mocMVC.get("/pesticidecount")
            .andDo { print() }
            .andExpect { status{isOk()} }
    }

    @Test
    fun pesticideCountByAvailableQty() {
        mocMVC.get("/pesticidecountbyavailableqty")
            .andDo { print() }
            .andExpect { status{isOk()} }
    }

}