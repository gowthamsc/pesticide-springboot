package com.pestManage.pesticideMange.DAO

import com.pestManage.pesticideMange.model.Pesticides
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PesticideDAO : MongoRepository<Pesticides, String> {
    fun existsByPestId(pestId: String): Boolean
    fun existsByPestName(pestName: String): Boolean
    fun findPesticidesByPestName(pestName: String): Pesticides

    fun countByAvlQtyInGm(avlQtyInGm:Int):Long

}