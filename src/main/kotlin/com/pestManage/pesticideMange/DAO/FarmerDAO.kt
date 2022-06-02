package com.pestManage.pesticideMange.DAO

import com.pestManage.pesticideMange.model.Farmer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FarmerDAO : MongoRepository<Farmer, String> {
    fun existsByEmail(email: String): Boolean
    fun existsByEmailAndPassword(email: String, password: String): Boolean
}


