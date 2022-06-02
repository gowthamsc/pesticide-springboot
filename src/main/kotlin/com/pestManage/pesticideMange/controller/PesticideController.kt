package com.pestManage.pesticideMange.controller

import com.pestManage.pesticideMange.model.Pesticides
import com.pestManage.pesticideMange.model.PesticidesUpdateTemp
import com.pestManage.pesticideMange.service.PesticideService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/pesticide")
@CrossOrigin
class PesticideController(var pesticideService: PesticideService) {

    @PostMapping
    fun addPesticide(@Valid @RequestBody pesticide: Pesticides): Pesticides =
        this.pesticideService.addPesticide(pesticide)

    @PutMapping("/update/{id}")
    fun update(@PathVariable id: String, @RequestBody pesticidesUpdateTemp: PesticidesUpdateTemp): Pesticides =
        this.pesticideService.updatePesticide(id, pesticidesUpdateTemp)

    @GetMapping("/{id}")
    fun getPesticideById(@PathVariable id: String): Pesticides = this.pesticideService.viewPesticideById(id)

    @GetMapping
    fun getAllPesticides(): List<Pesticides> = this.pesticideService.viewAllPesticides()

    @DeleteMapping("/{id}")
    fun deletePesticidesById(@PathVariable id: String): String = this.pesticideService.deletePesticide(id)
}