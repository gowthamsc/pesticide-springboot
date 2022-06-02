package com.pestManage.pesticideMange.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.pestManage.pesticideMange.DAO.DiseaseDAO
import com.pestManage.pesticideMange.DAO.PesticideDAO
import com.pestManage.pesticideMange.model.*
import com.pestManage.pesticideMange.service.ConsultantService
import com.pestManage.pesticideMange.service.DiseaseService
import com.pestManage.pesticideMange.service.FarmerService
import org.junit.jupiter.api.Test


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.*
import org.springframework.web.multipart.MultipartFile

@SpringBootTest
@AutoConfigureMockMvc
class AddCartControllerTest (
    @Autowired val mocMVC: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val diseaseDAO: DiseaseDAO,
    @Autowired val pesticideDAO: PesticideDAO,
    @Autowired val diseaseService: DiseaseService,
    @Autowired val farmerService: FarmerService,
    @Autowired val consultantService: ConsultantService
){

    @Test
    fun agreePayment() {
        //adding farmer
        var farmer = Farmer("farmerTest@gmail.com","gowtham","9994501743", Address(65,"nkl","nkl","tn","100002") )
        mocMVC.post("/farmer"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(farmer)
        }.andDo { print() }
            .andExpect { status{isOk()} }
        var farmerTemp = farmerService.getFarmerById(farmer.email)

        //update farmer
        var farmerTemp1 = FarmerTemp("farmerTest@gmail.com","gowtham","9994501743",65,"nkl","nkl","tn","100002" )
        mocMVC.put("/farmer/${farmer.email}"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(farmerTemp1)
        }.andDo { print() }
            .andExpect { status{isOk()} }

        //login farmer
        var loginDTOFarmer =LoginDTO(farmer.email,farmerTemp.password!!)
        mocMVC.put("/login"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(loginDTOFarmer)
        }.andDo { print() }
            .andExpect { status{isOk()} }

        // duplicate farmer
        mocMVC.post("/farmer"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(farmer)
        }
            .andExpect { status{isBadRequest()} }


        //get farmer by id
        mocMVC.get("/farmer/${farmer.email}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get all farmer
        mocMVC.get("/farmer")
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }


        //adding disease
        val file: MultipartFile =  MockMultipartFile("jpeg","622ec116fc601205a2f2d35a.jpeg","image/jpeg","622ec116fc601205a2f2d35a.jpeg".byteInputStream())

        diseaseService.addDisease(file, farmer.email, "*Test Sample*", 1)
        diseaseService.addDisease(file, farmer.email, "*Test Sample2*", 1)
        var disease  = this.diseaseDAO.findDiseaseByDiseaseDescription("*Test Sample*")
        var secondDisease  = this.diseaseDAO.findDiseaseByDiseaseDescription("*Test Sample2*")


        //get disease
        mocMVC.get("/disease/${disease.dieaseId}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get all disease
        mocMVC.get("/disease")
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }

        var consultant = Consultant(
            "consultantTest@gmail.com", "Consultant","161Cs157$", "gowtham", "9994501743", false,
            ConsultantAddress(65, "nkl", "nkl","TN", "637210")
        )
        mocMVC.post("/consultant") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(consultant)
        }.andDo { print() }
            .andExpect { status { isOk() } }
        var consultantTemp = consultantService.viewConsultant(consultant.consultantEmail)

        //login consultant
        var loginDTOConsultant =LoginDTO(consultant.consultantEmail, consultantTemp.consultantPassword!!)
        mocMVC.put("/login"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginDTOConsultant)
        }.andDo { print() }
            .andExpect { status{isOk()} }


        //duplicate entry
        mocMVC.post("/consultant") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(consultant)
        }.andExpect { status { isBadRequest() } }

        //get consultant
        mocMVC.get("/consultant/${consultant.consultantEmail}")
            .andDo { println() }
            .andExpect {
                status { isOk() }
            }

        //get all consultant
        mocMVC.get("/consultant")
            .andDo { println() }
            .andExpect {
                status { isOk() }
            }

        //update consultant
        var consultantTemp1 = ConsultantTemp(
            "consultantTest@gmail.com",  "gowtham s", "9994501743",
            65, "nkl", "nkl","TN", "637210"
        )
        mocMVC.put("/consultant/${consultant.consultantEmail}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(consultantTemp1)
        }.andDo { print() }
            .andExpect { status { isOk() } }


        var pesticide = Pesticides(null,"*Test*",100,90.0)
        mocMVC.post("/pesticide"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(pesticide)
        }.andDo { print() }
            .andExpect { status{isOk()} }
        var pesticideTemprory = this.pesticideDAO.findPesticidesByPestName("*Test*")

        //update pesticide
        var pesticidesUpdateTemp =PesticidesUpdateTemp(pesticideTemprory.pestName,200,60.0)
        mocMVC.put("/pesticide/update/${pesticideTemprory.pestId}"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(pesticidesUpdateTemp)
        }.andDo { print() }
            .andExpect { status{isOk()} }

        //duplicate entry
        mocMVC.post("/pesticide"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(pesticide)
        }.andDo { print() }
            .andExpect { status{isBadRequest()} }

        //get pesticide
        mocMVC.get("/pesticide/${pesticideTemprory.pestId}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get all pesticide
        mocMVC.get("/pesticide")
            .andDo { print() }
            .andExpect { status{isOk()} }

        var delivery = Delivery("deliveryTestController@gmail.com","Delivery Person","181Cs158$","Delivery Person","9994561734",
            DeliverAddress(12,"NKL","NKL","TN","100001"))
        mocMVC.post("/deliverperson"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(delivery)
        }.andDo { print() }
            .andExpect { status{isOk()} }


        /******************************************Change review for disease ************************************************/


        //change review status for disease 1
        var review= TempVariableForAddCartPest(consultant.consultantEmail,disease.dieaseId)
        mocMVC.put("/review/changereviewstatus/${consultant.consultantEmail}/${disease.dieaseId}"){
        }
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }

        //check review status by consultant
        mocMVC.get("/disease/reviewstatus/${consultant.consultantEmail}")
            .andDo { print() }
            .andExpect { status{isOk()} }


        //change review status for disease 2
        var secondDiseaseReview= TempVariableForAddCartPest(consultant.consultantEmail, secondDisease.dieaseId)
        mocMVC.put("/review/changereviewstatus/${consultant.consultantEmail}/${secondDisease.dieaseId}"){

        }
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }

        // added pesticides to cart
        var tempPest:MutableList<TempPest> = mutableListOf()
        tempPest.add(TempPest(pesticideTemprory.pestId!!,20))
        var addingCart=TempPestCart(consultant.consultantEmail,disease.dieaseId!!,"test","prescription",tempPest)
        mocMVC.put("/review/cart"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(addingCart)
        }
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }


        //adding suggestions
        var addingCartWithoutPesticide=TempPestCart(consultant.consultantEmail,secondDisease.dieaseId!!,"suggestion added","prescription",tempPest)
        mocMVC.put("/review/cart"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(addingCartWithoutPesticide)
        }
            .andDo { println() }
            .andExpect {
                status{isOk()}
            }



        //before adding cart not able to do shipment
        var paythroughDeclined="Declined"
        var paymentID_3 ="Not Paid"
        mocMVC.put("/review/farmeragree/${disease.dieaseId}/${paythroughDeclined}/${paymentID_3}")
            .andExpect {
                status{isOk()}
            }

        //before adding cart not able to do shipment
        var paythrough="Online Payment"
        var paymentID_2 ="xyz667hfgjd"
        mocMVC.put("/review/farmeragree/${secondDisease.dieaseId}/${paythrough}/${paymentID_2}")
            .andExpect {
                status{isOk()}
            }


        var update_2 =ShipmentTemp(secondDisease.dieaseId!!,"Intimation","Not Paid")
        mocMVC.put("/updateshipment"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(update_2)
        }
            .andDo { print() }
            .andExpect { status{isOk()} }



        var update_3 =ShipmentTemp(secondDisease.dieaseId!!,"Refused","Not Paid")
        mocMVC.put("/updateshipment"){
            contentType = MediaType.APPLICATION_JSON
            content= objectMapper.writeValueAsString(update_3)
        }
            .andDo { print() }
            .andExpect { status{isOk()} }


        /****************************************** review completed ************************************************/


        //get disease count by farmer
        mocMVC.get("/diseasecountbyfarmer/${farmer.email}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get disease count by consultant
        mocMVC.get("/diseasecountbyconsultant/${consultant.consultantEmail}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get disease count by consultant and review
        var reviewFarmer = "Completed"
        mocMVC.get("/diseasecountbyconsultantandreview/${consultant.consultantEmail}/${reviewFarmer}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get disease count by farmer and review
        var reviewConsultant = "InProgress"
        mocMVC.get("/diseasecountbyfarmerandreviewstatus/${farmer.email}/${reviewConsultant}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get disease count by review
        var reviewStatus = "Pending"
        mocMVC.get("/diseasecountbyreviewstatus/${reviewStatus}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get shipment count by shipment
        var shipmentStatus = "Delivered"
        mocMVC.get("/shipmentcount/${shipmentStatus}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get disease by consultant
        mocMVC.get("/disease/consultant/${consultant.consultantEmail}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get disease by farmer
        mocMVC.get("/disease/farmer/${farmer.email}")
            .andDo { print() }
            .andExpect { status{isOk()} }


        //get farmer by disease id
        mocMVC.get("/farmer/disease/${disease.dieaseId}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get orderhistory by id
        mocMVC.get("/farmer/orderhistory/${disease.dieaseId}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //get all orderhistory
        mocMVC.get("/orderhistory")
            .andDo { print() }
            .andExpect { status{isOk()} }


        //change farmer password
        var farmer_Password ="171Cs157$"
        mocMVC.put("/farmer/changepassword/${farmer.email}/${farmerTemp.password}/${farmer_Password}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //change consultant password
        var consultant_Password ="171Cs157$"
        mocMVC.put("/consultant/${consultant.consultantEmail}/${consultantTemp.consultantPassword}/${consultant_Password}")
            .andDo { print() }
            .andExpect { status{isOk()} }

        //delete pesticide
        mocMVC.delete("/pesticide/${pesticideTemprory.pestId}")
            .andDo { print() }
            .andExpect { status{isOk()} }


        mocMVC.get("/forgetpassword/${consultant.consultantEmail}"){
        }.andDo { print() }
            .andExpect { status{isOk()} }

        //delete consultant
        mocMVC.delete("/consultant/consultantTest@gmail.com")
            .andDo { println() }
            .andExpect {
                status { isOk() }
            }


        //delete disease1
        mocMVC.delete("/disease/${disease.dieaseId}")
            .andExpect {
                status{isOk()}
            }
        //delete disease2
        mocMVC.delete("/disease/${secondDisease.dieaseId}")
            .andExpect {
                status{isOk()}
            }


//        //logout consultant
//        var logoutDTOConsultant = LogoutDTO(consultant.consultantEmail, "Consultant")
//        mocMVC.put("/logout"){
//            contentType = MediaType.APPLICATION_JSON
//            content= objectMapper.writeValueAsString(logoutDTOConsultant)
//        }.andDo { print() }
//            .andExpect { status{isOk()} }



        mocMVC.get("/forgetpassword/${farmer.email}"){
        }.andDo { print() }
            .andExpect { status{isOk()} }


        //delete farmer
        mocMVC.delete("/farmer/${farmer.email}")
            .andExpect {
                status{isOk()}
            }

        //delete delivery person
        mocMVC.delete("/deliverperson/${delivery.email}")
            .andDo { print() }
            .andExpect { status{isOk()} }
}


    @Test
    fun `should get NOT FOUNF for invalid disease`(){
        mocMVC.get("/disease/623170835a465e3e8e83b400")
            .andDo { print() }
            .andExpect { status{isBadRequest()} }
    }


    fun `should do faNOT Found by getting invalid farmer`(){
        var email="test@gmail.com"
        mocMVC.get("/farmer/$email")
            .andExpect { status{isBadRequest()} }
    }


    @Test
    fun `should get Not Found for invalid farmer`(){
        mocMVC.delete("/farmer/farmerTest@gmail.com")
            .andExpect {
                status{isBadRequest()}
            }
    }
    @Test
    fun `should get NOT FOUND for invalid email`() {
        mocMVC.get("/consultant/demo@gmail.com")
            .andDo { println() }
            .andExpect {
                status { isBadRequest() }
            }
    }
//    @Test
//    fun `should get No Content for empty list of consultant`(){
//        mocMVC.get("/consultant")
//            .andDo { println() }
//            .andExpect {
//                status { isBadRequest() }
//            }
//    }

    @Test
    fun `should get NOT FOUND on delete`() {
        mocMVC.delete("/consultant/demo@gmail.com")
            .andDo { println() }
            .andExpect {
                status { isBadRequest() }
            }
    }



    @Test
    fun `should get Not Found for invalid pest id`(){
        mocMVC.get("/pesticide/6231ba2a4fd7930ff0652989")
            .andExpect { status{isBadRequest()} }
    }

    @Test
    fun `should get Not found for invalid delete`(){
        mocMVC.delete("/pesticide/6231c21013b1e13fdef43630")
            .andExpect { status{isBadRequest()} }
    }
}