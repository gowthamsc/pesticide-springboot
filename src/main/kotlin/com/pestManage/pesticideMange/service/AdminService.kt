package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.model.Admin
import com.pestManage.pesticideMange.model.AdminTemp


interface AdminService {
    fun addAdmin(adminTemp: AdminTemp): Admin
    fun viewAdmin(email: String): Admin
    fun deleteAdmin(email: String): String
    fun updateAdmin(email: String, adminTemp: AdminTemp): Admin
    fun chnagePassword(email: String, oldPassword: String, newPassword: String): String
}