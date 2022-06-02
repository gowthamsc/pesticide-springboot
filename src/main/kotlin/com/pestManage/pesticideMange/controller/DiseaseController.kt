package com.pestManage.pesticideMange.controller

import com.pestManage.pesticideMange.model.Disease
import com.pestManage.pesticideMange.model.DiseaseImageDTO
import com.pestManage.pesticideMange.model.Farmer
import com.pestManage.pesticideMange.service.DiseaseService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/disease")
@CrossOrigin
class DiseaseController(var diseaseService: DiseaseService) {
    companion object {
        var uploadDirectory: String = System.getProperty("user.dir") + "/src/main/webapp/imgdata"
    }

    @PostMapping(
        value = ["/add"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addDisease(@Valid diseaseImageDTO: DiseaseImageDTO): Farmer {
        return this.diseaseService.addDisease(
            diseaseImageDTO.diseaseImage,
            diseaseImageDTO.email,
            diseaseImageDTO.diseaseDescription,
            diseaseImageDTO.acre
        )
    }

    @GetMapping("/{id}")
    fun getDiseasesById(@PathVariable id: String): Disease = this.diseaseService.viewDiseaseByDiseaseId(id)

    @GetMapping
    fun getalldiseases(): List<Disease> = this.diseaseService.viewAllDisease()

    @GetMapping("/farmer/{email}")
    fun getalldiseasebyfarmer(@PathVariable email: String): List<Disease> = this.diseaseService.viewFarmerDisease(email)

    @GetMapping("/consultant/{email}")
    fun getalldiseasebyconsultant(@PathVariable email: String): List<Disease> =
        this.diseaseService.viewConsultantDisease(email)

    @DeleteMapping("/{id}")
    fun deleteDisease(@PathVariable id: String): String = this.diseaseService.deleteDisease(id)

    @GetMapping("/reviewstatus/{email}")
    fun checkByConsultant(@PathVariable email: String): String = this.diseaseService.checkByConsultant(email)
}