package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.model.Disease
import com.pestManage.pesticideMange.model.OrderHistory
import com.pestManage.pesticideMange.model.TempPest

interface PesticideAddedToCartService {
    fun reviewerStatus(consultantEmail: String, diseaseID: String): Disease
    fun addCart(
        consultantEmail: String,
        diseaseID: String,
        suggestion: String?,
        prescription: String?,
        pesticide: MutableList<TempPest>?
    ): OrderHistory
}