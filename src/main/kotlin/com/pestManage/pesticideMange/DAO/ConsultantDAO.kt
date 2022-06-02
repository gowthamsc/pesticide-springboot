package com.pestManage.pesticideMange.DAO

import com.pestManage.pesticideMange.model.Consultant
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ConsultantDAO : MongoRepository<Consultant, String> {
    fun existsByConsultantEmail(email: String): Boolean
    fun existsByConsultantEmailAndConsultantPassword(email: String, password: String): Boolean


}