package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.model.Consultant
import com.pestManage.pesticideMange.model.ConsultantTemp

interface ConsultantService {
    fun addConsultant(consultant: Consultant): Consultant
    fun viewConsultant(email: String): Consultant
    fun updateConsultant(email: String, consultantTemp: ConsultantTemp): Consultant
    fun changePassword(email: String, oldPassword: String, newPassword: String): Consultant
    fun viewAllConsultant(): MutableList<Consultant>
    fun deleteConsultant(email: String): String

}