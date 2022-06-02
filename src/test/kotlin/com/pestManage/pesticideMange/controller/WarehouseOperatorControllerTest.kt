package com.pestManage.pesticideMange.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.pestManage.pesticideMange.model.*
import com.pestManage.pesticideMange.service.WarehouseOperatorService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
 class WarehouseOperatorControllerTest(
    @Autowired val mocMVC: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val warehouseOperatorService: WarehouseOperatorService
) {

    @Test
    fun addOperator() {
        var warehouseOperator = WarehouseOperator("warehouseoperatoTestConrollerr@gmail.com","Warehouse Operator","161Cs157$","Test","9994501743",false,
            WarehouseOperatorAddress(10,"nkl","nkl","Tn","100001"))
        mocMVC.post("/warehouseoperator"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(warehouseOperator)
        }.andDo { print() }
            .andExpect { status{isOk()} }
        var operatorTemp = this.warehouseOperatorService.viewWarehouseOperator(warehouseOperator.operatorEmail)


        //update operator
        var warehouseOperatorTemp1 = WarehouseOperatorTemp("warehouseoperatoTestConrollerr@gmail.com","Warehouse Operator","9994501743",10,"nkl","nkl","Tn","100001")
        mocMVC.put("/warehouseoperator/${operatorTemp.operatorEmail}"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(warehouseOperatorTemp1)
        }.andDo { print() }
            .andExpect { status{isOk()} }


        //duplicate entry
        mocMVC.post("/warehouseoperator"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(warehouseOperator)
        }.andDo { print() }
            .andExpect { status{isBadRequest()} }

        //get operator
        mocMVC.get("/warehouseoperator/${warehouseOperator.operatorEmail}")
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }

        //get all operator
        mocMVC.get("/warehouseoperator")
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }

        //change password
        mocMVC.put("/warehouseoperator/${warehouseOperator.operatorEmail}/${operatorTemp.operatorPassword}/191Cs159$")
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }

        //delete operator
        mocMVC.delete("/warehouseoperator/${warehouseOperator.operatorEmail}")
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }
    }


    @Test
    fun `should get Not Found for invalid email on find`(){
        mocMVC.get("/warehouseoperator/warehouseTest1@gmail.com")
            .andDo { println() }
            .andExpect {
                status{isBadRequest()}
            }
    }

    @Test
    fun `should get Not Found for invalid email on delete`(){
        mocMVC.delete("/warehouseoperator/warehouseTest1@gmail.com")
            .andDo { println() }
            .andExpect {
                status{isBadRequest()}
            }
    }
}