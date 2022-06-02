package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.model.Disease
import com.pestManage.pesticideMange.model.Farmer
import org.springframework.web.multipart.MultipartFile

interface DiseaseService {
    fun addDisease(file: MultipartFile, email: String, diseaseName: String, acre: Int): Farmer
    fun viewFarmerDisease(email: String): List<Disease>
    fun viewConsultantDisease(email: String): List<Disease>
    fun viewAllDisease(): MutableList<Disease>
    fun viewDiseaseByDiseaseId(diseaseId: String): Disease
    fun deleteDisease(id: String): String
    fun checkByConsultant(email: String): String
}