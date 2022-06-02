package com.pestManage.pesticideMange.DAO

import com.pestManage.pesticideMange.model.Admin
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminDAO : MongoRepository<Admin, String> {
    fun existsByEmail(email: String): Boolean
    fun existsByEmailAndPassword(email: String, password: String): Boolean

}