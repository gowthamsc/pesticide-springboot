package com.pestManage.pesticideMange.DAO

import com.pestManage.pesticideMange.model.PesticideAddedToCart
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PesticideAddedToCartDAO : MongoRepository<PesticideAddedToCart, String>