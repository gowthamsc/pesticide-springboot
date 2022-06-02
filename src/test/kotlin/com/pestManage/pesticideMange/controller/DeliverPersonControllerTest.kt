package com.pestManage.pesticideMange.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.pestManage.pesticideMange.model.DeliverAddress
import com.pestManage.pesticideMange.model.Delivery
import com.pestManage.pesticideMange.model.DeliveryTemp
import com.pestManage.pesticideMange.service.DeliverPersonService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
class DeliverPersonControllerTest(
    @Autowired val mocMVC: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val deliverPersonService: DeliverPersonService
) {

    @Test
    fun addDeliverPerson() {
        var delivery = Delivery("deliveryTestController@gmail.com","Delivery Person","181Cs158$","Delivery Person","9994561734",
            DeliverAddress(12,"NKL","NKL","TN","100001"))
        mocMVC.post("/deliverperson"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(delivery)
        }.andDo { print() }
            .andExpect { status{isOk()} }
        var deliverPersonTemp = deliverPersonService.viewByEmail(delivery.email)


        mocMVC.get("/deliverperson/${delivery.email}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        mocMVC.get("/deliverperson")
            .andDo { print() }
            .andExpect { status{isOk()} }


        var deliveryTemp1 = DeliveryTemp("deliveryTestController@gmail.com","Delivery Person","9994561734",
            12,"NKL","NKL","TN","100001")
        mocMVC.put("/deliverperson/${delivery.email}"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(deliveryTemp1)
        }
            .andDo { print() }
            .andExpect { status{isOk()} }


        mocMVC.put("/deliverperson/changepassword/${delivery.email}/${deliverPersonTemp.password}/191Cs159$")
            .andDo { print() }
            .andExpect { status{isOk()} }


        mocMVC.delete("/deliverperson/${delivery.email}")
            .andDo { print() }
            .andExpect { status{isOk()} }
    }
}