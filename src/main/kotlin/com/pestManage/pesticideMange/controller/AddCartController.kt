package com.pestManage.pesticideMange.controller

import com.pestManage.pesticideMange.model.Disease
import com.pestManage.pesticideMange.model.OrderHistory
import com.pestManage.pesticideMange.model.TempPestCart
import com.pestManage.pesticideMange.service.ConsultantService
import com.pestManage.pesticideMange.service.FarmerService
import com.pestManage.pesticideMange.service.PesticideAddedToCartService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/review")
@CrossOrigin
class AddCartController(
    var farmerService: FarmerService,
    var consultantService: ConsultantService,
    var pesticideAddedToCartService: PesticideAddedToCartService
) {


    @PutMapping("/farmeragree/{id}/{paythrough}/{paymentid}")
    fun agreePayment(
        @PathVariable id: String,
        @PathVariable paythrough: String,
        @PathVariable paymentid: String
    ): OrderHistory = this.farmerService.changePaymentAgree(id, paythrough, paymentid)


    @PutMapping("/changereviewstatus/{cemail}/{id}")
    fun changeReviewStatus(@PathVariable cemail: String, @PathVariable id: String): Disease {
        return this.pesticideAddedToCartService.reviewerStatus(
            cemail, id
        )
    }

    @PutMapping("/cart")
    fun addCart(@Valid @RequestBody tempPestCart: TempPestCart): OrderHistory {

        return this.pesticideAddedToCartService.addCart(
            tempPestCart.consultantEmail,
            tempPestCart.diseaseId!!,
            tempPestCart.suggestion,
            tempPestCart.prescription,
            tempPestCart.tempPest
        )
    }

}