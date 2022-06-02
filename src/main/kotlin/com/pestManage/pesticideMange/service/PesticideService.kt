package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.model.Pesticides
import com.pestManage.pesticideMange.model.PesticidesUpdateTemp

interface PesticideService {
    fun addPesticide(pesticide: Pesticides): Pesticides
    fun updatePesticideQty(pestId: String, qty: Int): Pesticides
    fun updatePesticidePrice(pestId: String, price: Double): Pesticides
    fun updatePesticide(pestId: String, pesticidesUpdateTemp: PesticidesUpdateTemp): Pesticides
    fun viewPesticideById(pesticideId: String): Pesticides
    fun viewAllPesticides(): MutableList<Pesticides>
    fun deletePesticide(pesticideId: String): String
}