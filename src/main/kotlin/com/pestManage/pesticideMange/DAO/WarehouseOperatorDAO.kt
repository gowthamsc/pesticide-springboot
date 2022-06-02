package com.pestManage.pesticideMange.DAO

import com.pestManage.pesticideMange.model.WarehouseOperator
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface WarehouseOperatorDAO : MongoRepository<WarehouseOperator, String> {
    fun existsByOperatorEmail(email: String): Boolean
    fun existsByOperatorEmailAndOperatorPassword(email: String, password: String): Boolean
}