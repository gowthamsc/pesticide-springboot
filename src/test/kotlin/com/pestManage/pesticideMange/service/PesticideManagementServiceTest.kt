package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.*
import com.pestManage.pesticideMange.exception.*
import com.pestManage.pesticideMange.model.*
import io.mockk.mockk
import org.assertj.core.api.Java6Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile

@SpringBootTest
internal class PesticideManagementServiceTest @Autowired constructor(
    var farmerService: FarmerService,
    var farmerDAO: FarmerDAO,
    var diseaseService: DiseaseService,
    var diseaseDAO: DiseaseDAO,
    var consultantService: ConsultantService,
    var consultantDAO: ConsultantDAO,
    var pesticideService: PesticideService,
    var pesticideDAO: PesticideDAO,
    var pesticideAddedToCartService: PesticideAddedToCartService,
    var shipmentService: ShipmentService,
    var deliverPersonService: DeliverPersonService

    ) {
    private val farmerdaomock:FarmerDAO = mockk()
    private val id:String = ObjectId().toString()


    @Test
    fun addFarmer() {
        var farmer1 = Farmer("farmerTestService@gmail.com","gowtham","9994501743", Address(65,"nkl","nkl","tn","637210") )
        assertThat(farmerService.addFarmer(farmer1)).isNotNull
        var farmer = this.farmerDAO.findById(farmer1.email).get()

        //duplicate entry of farmer
        var exception = assertThrows<AddException>(message = "Farmer already registered..."){
            farmerService.addFarmer(farmer)
        }

        //get farmer
        assertThat(farmerService.getFarmerById(farmer.email)).isEqualTo(farmer)

//        //get all farmer
//        assertThat(farmerService.getAllFarmer()).isEqualTo(farmerDAO.findAll())
//
        //add disease
        val file: MultipartFile =  MockMultipartFile("jpeg","622ec116fc601205a2f2d35a.jpeg","image/jpeg","622ec116fc601205a2f2d35a.jpeg".byteInputStream())
        assertThat(diseaseService.addDisease(file,farmer.email,"*Test Sample first Disease*",1)).isNotNull
        assertThat(diseaseService.addDisease(file,farmer.email,"*Test Sample second Disease*",1)).isNotNull
        var firstDisease = diseaseDAO.findDiseaseByDiseaseDescription("*Test Sample first Disease*")
        var secondDisease = diseaseDAO.findDiseaseByDiseaseDescription("*Test Sample second Disease*")

        //view disease by id
        assertThat(diseaseService.viewDiseaseByDiseaseId(firstDisease.dieaseId!!)).isEqualTo(firstDisease)

        //view all disease
        assertThat(diseaseService.viewAllDisease()).isEqualTo(diseaseDAO.findAll())

        //add consultant
        var consultant1=
            Consultant("consultantTestService@gmail.com","Consultant","161Cs157$","gowtham","9994501743",null, ConsultantAddress(34,"nkl","NKL","Tn","637210"))
        assertThat(consultantService.addConsultant(consultant1)).isNotNull
        var consultant =this.consultantDAO.findById(consultant1.consultantEmail).get()


        //duplicate entry of consultant
        assertThrows<AddException>(message = "Consultant already registered"){
            consultantService.addConsultant(consultant)
        }

        //view consultant
        assertThat(consultantService.viewConsultant(consultant.consultantEmail)).isEqualTo(consultant)

        //view all consultant
        assertThat(consultantService.viewAllConsultant()).isEqualTo(consultantDAO.findAll())

        //add pesticides
        var pesticide1 = Pesticides(id,"*Test Pesticide*",100,90.0)
        assertThat(pesticideService.addPesticide(pesticide1)).isNotNull
        var pesticide =this.pesticideDAO.findPesticidesByPestName("*Test Pesticide*")

        //duplicate entry
        org.junit.jupiter.api.assertThrows<AddException>(message = "Pesticide already exists...") {
            pesticideService.addPesticide(pesticide)
        }

        //view pesticide
        assertThat(pesticideService.viewPesticideById(pesticide.pestId!!)).isEqualTo(pesticide)

        //view all pesticide
        assertThat(this.pesticideService.viewAllPesticides()).isNotNull

        //update pesticide price
        var pesticideUpdatePrice:Pesticides = this.pesticideService.updatePesticidePrice(pesticide.pestId!!,80.00)
        assertThat(pesticideService.viewPesticideById(pesticide.pestId!!)).isEqualTo(pesticideUpdatePrice)

        //update pesticide quantity
        var pesticideUpdateQty:Pesticides = this.pesticideService.updatePesticideQty(pesticide.pestId!!,800)
        assertThat(pesticideService.viewPesticideById(pesticide.pestId!!)).isEqualTo(pesticideUpdateQty)


        //add delivery person
        var delivery = Delivery("deliveryTestController@gmail.com","Delivery Person","181Cs158$","Delivery Person","9994561734",
            DeliverAddress(12,"NKL","NKL","TN","100001"))
        assertThat(deliverPersonService.addDeliverPerson(delivery)).isNotNull



        /************************************* Review of disease *****************************************************************/
        //change review status for disease 1
        assertThat(this.pesticideAddedToCartService.reviewerStatus(consultant.consultantEmail,firstDisease.dieaseId!!)).isNotNull

        //change review status for disease 2
        assertThat(this.pesticideAddedToCartService.reviewerStatus(consultant.consultantEmail,secondDisease.dieaseId!!)).isNotNull


        //adding pesticides to cart
        var tempPestCart:MutableList<TempPest> = mutableListOf()
        tempPestCart.add(TempPest(pesticide.pestId!!,1))
        assertThat(this.pesticideAddedToCartService.addCart(consultant.consultantEmail,firstDisease.dieaseId!!,"suggestion","prescription",tempPestCart)).isNotNull

        //adding Suggestions to cart
        assertThat(this.pesticideAddedToCartService.addCart(consultant.consultantEmail,secondDisease.dieaseId!!,"suggestions added","prescription",tempPestCart)).isNotNull


//        //farmer agree
//        assertThat(farmerService.changePaymentAgree(firstDisease.dieaseId!!,"Not Paid","")).isNotNull()
//        assertThat(farmerService.changePaymentAgree(secondDisease.dieaseId!!,"Not Paid","")).isNotNull()

        //farmer agree
        assertThat(farmerService.changePaymentAgree(secondDisease.dieaseId!!,"Online Payment","ghdhf67gjt67")).isNotNull
        assertThat(farmerService.changePaymentAgree(firstDisease.dieaseId!!,"Declined","")).isNotNull

        //shipment update
        assertThat(this.shipmentService.deliveryStatus(ShipmentTemp(secondDisease.dieaseId!!,"Intimation","Paid"))).isNotNull
        //shipment update
        assertThat(this.shipmentService.deliveryStatus(ShipmentTemp(secondDisease.dieaseId!!,"Refused","Paid"))).isNotNull


        /*********************************** Review completed ***********************************************************************/
        //delete pesticide
        assertThat(this.pesticideService.deletePesticide(pesticide.pestId!!)).isEqualTo("Deleted Successfully")

        //delete consultant
        assertThat(consultantService.deleteConsultant(consultant.consultantEmail)).isEqualTo("Deleted Successfully")

        //delete disease
        assertThat(diseaseService.deleteDisease(firstDisease.dieaseId!!)).isEqualTo("Deleted Successfully")
        assertThat(diseaseService.deleteDisease(secondDisease.dieaseId!!)).isEqualTo("Deleted Successfully")

        //delete farmer
        assertThat(farmerService.deleteById(farmer1.email)).isEqualTo("Deleted successfully")

        //delete delivery person
        assertThat(deliverPersonService.deleteDeliverPerson(delivery.email)).isEqualTo("Deleted Successfully")

    }

    @Test
    fun `should get Not found`(){
        var exception = assertThrows<GetException>(message = "Farmer not found, Please register...."){
            farmerService.getFarmerById("ko@gmail.com")
        }
    }

//    @Test
//    fun `should get NO content for null`(){
//        var exception = assertThrows<GetAllException>(message = "No farmers found, Please register...."){
//            farmerService.getAllFarmer()
//        }
//    }


}