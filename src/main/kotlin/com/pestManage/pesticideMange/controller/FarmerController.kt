package com.pestManage.pesticideMange.controller


import com.pestManage.pesticideMange.model.Farmer
import com.pestManage.pesticideMange.model.FarmerTemp
import com.pestManage.pesticideMange.model.OrderHistory
import com.pestManage.pesticideMange.service.FarmerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/farmer")
@CrossOrigin
class FarmerController(private var farmerService: FarmerService) {

    @PostMapping
    fun addFarmer(@Valid @RequestBody farmer: Farmer): ResponseEntity<Farmer> {
        return ResponseEntity(this.farmerService.addFarmer(farmer), HttpStatus.OK)
    }

    @GetMapping("/{email}")
    fun getFarmer(@PathVariable email: String): Farmer = this.farmerService.getFarmerById(email)

    @GetMapping("/disease/{id}")
    fun getFarmerByDisease(@PathVariable id: String): Farmer = this.farmerService.getFarmerByDiseaseId(id)

    @GetMapping("/orderhistory/{id}")
    fun getOrderHistoryByDisease(@PathVariable id: String): OrderHistory =
        this.farmerService.getOrderHistoryByDisease(id)

    @GetMapping
    fun getAllFarmer(): List<Farmer> {
        return this.farmerService.getAllFarmer()
    }

    @PutMapping("/{email}")
    fun updateFarmer(@PathVariable email: String, @RequestBody farmer: FarmerTemp): Farmer =
        this.farmerService.updateFarmer(email, farmer)

    @PutMapping("/changepassword/{email}/{oldpassword}/{newpassword}")
    fun changeFarmerPassword(
        @PathVariable email: String,
        @PathVariable oldpassword: String,
        @PathVariable newpassword: String
    ): Farmer {
        return this.farmerService.changePassword(email, oldpassword, newpassword)
    }

    @DeleteMapping("/{email}")
    fun deleteFarmer(@PathVariable email: String): String = this.farmerService.deleteById(email)

}