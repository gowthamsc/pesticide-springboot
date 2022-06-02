package com.pestManage.pesticideMange.DAO

import com.pestManage.pesticideMange.model.Disease
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DiseaseDAO : MongoRepository<Disease, String> {

    @Query("{'email' : ?0 }")
    fun findDiseaseByEmail(email: String): kotlin.collections.List<Disease>
    fun findDiseaseByDiseaseDescription(diseaseDescription: String): Disease
    fun existsDiseaseByEmail(email: String): Boolean
    fun existsDiseaseByConsultantEmail(email: String): Boolean
    fun findDiseaseByConsultantEmail(email: String): kotlin.collections.List<Disease>
    fun existsDiseaseByConsultantEmailAndReviewStatus(email: String, reviewStatus: String): Boolean


    fun countByEmail(email:String):Long
    fun countByConsultantEmailAndReviewStatus(email: String, reviewStatus: String):Long
    fun countByConsultantEmail(email: String):Long
    fun countByReviewStatus(reviewStatus: String):Long
    fun countByEmailAndReviewStatus(email: String, reviewStatus: String):Long

}