package com.pestManage.pesticideMange.controller

import com.pestManage.pesticideMange.model.Consultant
import com.pestManage.pesticideMange.model.ConsultantTemp
import com.pestManage.pesticideMange.service.ConsultantService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/consultant")
@CrossOrigin
class ConsultantController(
    var consultantService: ConsultantService
) {
    @PostMapping
    fun addConsultant(@Valid @RequestBody consultant: Consultant): Consultant =
        this.consultantService.addConsultant(consultant)

    @GetMapping("/{email}")
    fun getConsultant(@PathVariable email: String): Consultant = this.consultantService.viewConsultant(email)

    @GetMapping
    fun getAllConsultant(): List<Consultant> = this.consultantService.viewAllConsultant()

    @PutMapping("/{email}")
    fun updateConsultant(@PathVariable email: String, @RequestBody consultantTemp: ConsultantTemp): Consultant =
        this.consultantService.updateConsultant(email, consultantTemp)

    @PutMapping("/{email}/{oldPassword}/{newPassword}")
    fun changePassowrd(
        @PathVariable email: String,
        @PathVariable oldPassword: String,
        @PathVariable newPassword: String
    ): Consultant = this.consultantService.changePassword(email, oldPassword, newPassword)

    @DeleteMapping("/{email}")
    fun deleteConsultant(@PathVariable email: String): String = this.consultantService.deleteConsultant(email)

}