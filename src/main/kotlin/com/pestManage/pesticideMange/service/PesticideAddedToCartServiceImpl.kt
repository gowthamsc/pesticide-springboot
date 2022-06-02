package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.*
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class PesticideAddedToCartServiceImpl(
    var farmerDAO: FarmerDAO,
    var consultantDAO: ConsultantDAO,
    var diseaseDAO: DiseaseDAO,
    var pesticideDAO: PesticideDAO,
    var diseaseService: DiseaseService,
    var farmerService: FarmerService,
    var consultantService: ConsultantService,
    var pesticideService: PesticideService,
    var pesticideAddedToCartDAO: PesticideAddedToCartDAO,
    var orderDAO: OrderDAO,
    var sendEmailServiceImpl: SendEmailServiceImpl,
    var countService: CountService
) : PesticideAddedToCartService {


    override fun reviewerStatus(consultantEmail: String, tempDiseaseId: String): Disease {
        var diseaseID = tempDiseaseId
        var consultant: Consultant = this.consultantService.viewConsultant(consultantEmail)
        var disease: Disease = this.diseaseService.viewDiseaseByDiseaseId(diseaseID)
        var order = this.orderDAO.findById(diseaseID).get()
        var farmer: Farmer = this.farmerService.getFarmerById(disease.email!!)
        if (disease.reviewStatus.equals("Pending")) {
            disease.reviewStatus = "InProgress"
            order.shipmentStatus = "Your disease is taken for review."
            disease.consultantName = consultant.consultantName
            disease.consultantPhoneNo = consultant.consultantPhoneNo
            disease.consultantEmail = consultant.consultantEmail
            diseaseDAO.save(disease)
            order.paid = ""
            order.agree = ""
            farmer.diseease?.map { d -> d.reviewStatus = "InProgress" }
            farmer.diseease?.map { d -> d.consultantName = consultant.consultantName }
            farmer.diseease?.map { d -> d.consultantPhoneNo = consultant.consultantPhoneNo }
            farmer.diseease?.map { d -> d.consultantEmail = consultant.consultantEmail }
            order.disease?.consultantEmail = consultant.consultantEmail
            order.disease?.consultantName = consultant.consultantName
            order.disease?.consultantPhoneNo = consultant.consultantPhoneNo
            order.disease?.reviewStatus = "InProgress"
            orderDAO.save(order)
            farmerDAO.save(farmer)
            var subject: String = "Pesticide Management Service Notification"
            var body: String =
                "Hi ${farmer.name},\n Your crop disease has been taken to review. The solution will be obtained shortly. \nThe reviewer details are as follows: \nConsultant Name: ${consultant.consultantName}\nConsultant Phone Number: ${consultant.consultantPhoneNo}\nConsultant Email: ${consultant.consultantEmail}.\n\n Thanks,\n Pesticide Management Team"
            sendEmailServiceImpl.sendMail(farmer.email, body, subject)
            return this.diseaseDAO.findById(diseaseID).get()
        } else
            throw GetException("Already consultation in progress by the consultant: " + consultant.consultantName + " and contact number:" + consultant.consultantPhoneNo)

    }

    //add Pesticide to cart or add suggestions
    override fun addCart(
        consultantEmail: String,
        tempDiseaseId: String,
        suggestion: String?,
        prescription: String?,
        tempPesticide: MutableList<TempPest>?
    ): OrderHistory {
        var consultant: Consultant = this.consultantService.viewConsultant(consultantEmail)
        var diseaseID = tempDiseaseId
        var disease: Disease = this.diseaseService.viewDiseaseByDiseaseId(tempDiseaseId)
        var farmer: Farmer = this.farmerService.getFarmerById(disease.email!!)
        var order: OrderHistory = orderDAO.findById(diseaseID).get()
        var pestTotalPay: Double = 0.0
        var pestCount = 0
        if (disease.reviewStatus.equals("InProgress")) {
            for (tempPestId in tempPesticide!!) {
                var pestID = tempPestId.objectid
                var pesticide: Pesticides? = this.pesticideService.viewPesticideById(pestID)
                var reqQty: Int = tempPestId.reqQty
                pesticide?.avlQtyInGm = pesticide?.avlQtyInGm?.minus(reqQty)!!
                var price: Double? = pesticide.pricePerGm.times(reqQty)
                pestTotalPay = pestTotalPay.plus(price!!)
                pestCount = pestCount.plus(1)
                var pesticideAddedToCart = PesticideAddedToCart()
                pesticideAddedToCart.pesticideName = pesticide.pestName
                pesticideAddedToCart.pesticideQtyReq = reqQty
                pesticideAddedToCart.pesticidePricePerUnit = price
                pesticideAddedToCartDAO.save(pesticideAddedToCart)
                pesticideDAO.save(pesticide)
                disease.pesticide?.add(pesticideAddedToCart)
                order.pesticides?.add(PesticideAddedToCart(null, pesticide.pestName, reqQty, price))

            }
            if(countService.countByFarmerAndReviewStatus(farmer.email, "Completed")>=1){
                order.consultationFee = 250.00
            }
            else{
                order.consultationFee = 150.00
            }
            val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val now = LocalDateTime.now()
            order.reviewedDate = dtf.format(now)
            order.agree = "Pending"
            order.totalPay = pestTotalPay
            order.count = pestCount
            order.shipmentStatus = "Solution added"
            order.disease?.reviewStatus = "Completed"
            order.suggestion = suggestion
            order.prescription = prescription
            order.consultFeePaid ="Not Paid"
            order.disease?.suggestion = suggestion
            orderDAO.save(order)
            farmer.orderHistory?.add(order)
            disease.reviewStatus = "Completed"
            disease.suggestion = suggestion
            diseaseDAO.save(disease)
            farmer.diseease!!.filter { it == disease }.map { d -> d.reviewStatus = "Completed" }
            farmer.diseease!!.filter { it == disease }.map { d -> d.suggestion = suggestion }
            farmerDAO.save(farmer)
            var subject: String = "Pesticide Management Service Notification"
            var body: String =
                "Hi ${farmer.name},\n Your crop disease has been reviewd. The following pesticides are suggested by reviewer. Please login your account and get pesticide details. \nThe solutions are given by: \nConsultant Name: ${consultant.consultantName}\nConsultant Phone Number: ${consultant.consultantPhoneNo}\nConsultant Email: ${consultant.consultantEmail}.\n\n Thanks,\n Pesticide Management Team"
            sendEmailServiceImpl.sendMail(farmer.email, body, subject)
            return order
        } else {
            throw GetException("Consultation is in progress and the same reviewd by the consultant: " + consultant.consultantName + " and contact number:" + consultant.consultantPhoneNo)
        }

    }
}