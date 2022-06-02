package com.pestManage.pesticideMange.controller

import com.pestManage.pesticideMange.service.CountService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class CountController(var countService: CountService) {

    @GetMapping("/diseasecount")
    fun diseaseCount():Long{
        return countService.diseaseCount()
    }

    @GetMapping("/diseasecountbyfarmer/{email}")
    fun diseaseCountByFarmer(@PathVariable email:String):Long{
        return countService.diseaseCountByFarmer(email)
    }

    @GetMapping("/diseasecountbyconsultant/{email}")
    fun diseaseCountByConsultant(@PathVariable email:String):Long{
        return countService.diseaseCountByConsultant(email)
    }

    @GetMapping("/diseasecountbyconsultantandreview/{email}/{reviewStatus}")
    fun diseaseCountByConsultantAndReviewStatus(@PathVariable email:String,@PathVariable reviewStatus:String):Long{
        return countService.diseaseCountByConsultantAndReviewStatus(email,reviewStatus)
    }

    @GetMapping("/diseasecountbyreviewstatus/{reviewStatus}")
    fun countByReviewStatus(@PathVariable reviewStatus: String):Long{
        return countService.countByReviewStatus(reviewStatus)
    }

    @GetMapping("/diseasecountbyfarmerandreviewstatus/{email}/{reviewStatus}")
    fun countByFarmerAndReviewStatus(@PathVariable email:String,@PathVariable reviewStatus: String):Long{
        return countService.countByFarmerAndReviewStatus(email,reviewStatus)
    }

    @GetMapping("/consultantcount")
    fun consultantCount():Long{
        return countService.consultantCount()
    }

    @GetMapping("/warehouseoperatorcount")
    fun warehouseOperatorCount():Long{
        return countService.warehouseOperatorCount()
    }

    @GetMapping("/farmercount")
    fun farmerCount():Long{
        return countService.farmerCount()
    }

        @GetMapping("/deliverpersoncount")
    fun deliverPersonCount():Long{
        return countService.deliverPersonCount()
    }

    @GetMapping("/shipmentcount/{shipmentStatus}")
    fun shipmentCount(@PathVariable shipmentStatus:String):Long{
        return countService.shipmentCount(shipmentStatus)
    }

    @GetMapping("/pesticidecount")
    fun pesticideCount():Long{
        return countService.pesticideCount()
    }

    @GetMapping("/pesticidecountbyavailableqty")
    fun pesticideCountByAvailableQty():Long{
        return countService.pesticideOutOfStockCount()
    }

    @GetMapping("/deliverpersoncount/{email}")
    fun deliverPersonCount(@PathVariable email:String):Long{
        return countService.countDeliverListByMail(email)
    }

    @GetMapping("/deliverpersonshipmentcount/{email}/{status}")
    fun deliverPersonShipmentCount(@PathVariable email:String,@PathVariable status:String):Long{
        return countService.countDeliverpersonAndShipment(email,status)
    }
}