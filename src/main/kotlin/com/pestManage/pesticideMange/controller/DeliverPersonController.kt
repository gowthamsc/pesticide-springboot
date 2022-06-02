package com.pestManage.pesticideMange.controller

import com.pestManage.pesticideMange.model.Delivery
import com.pestManage.pesticideMange.model.DeliveryTemp
import com.pestManage.pesticideMange.service.DeliverPersonService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/deliverperson")
@CrossOrigin
class DeliverPersonController(var deliverPersonService: DeliverPersonService) {
    @PostMapping
    fun addDeliverPerson(@Valid @RequestBody delivery: Delivery): Delivery =
        this.deliverPersonService.addDeliverPerson(delivery)

    @GetMapping("/{email}")
    fun getDeliverPerson(@PathVariable email: String): Delivery = this.deliverPersonService.viewByEmail(email)

    @GetMapping
    fun getAllDeliverPerson(): List<Delivery> = this.deliverPersonService.viewAllDeliverPerson()

    @PutMapping("/{email}")
    fun updateDeliverPerson(@PathVariable email: String, @RequestBody deliveryTemp: DeliveryTemp): Delivery =
        this.deliverPersonService.updateDeliverPerson(email, deliveryTemp)

    @PutMapping("/changepassword/{email}/{oldpassword}/{newpassword}")
    fun changePassword(
        @PathVariable email: String,
        @PathVariable oldpassword: String,
        @PathVariable newpassword: String
    ): String = this.deliverPersonService.changePassword(email, oldpassword, newpassword)


    @DeleteMapping("/{email}")
    fun deleteDeliverPerson(@PathVariable email: String): String = this.deliverPersonService.deleteDeliverPerson(email)
}