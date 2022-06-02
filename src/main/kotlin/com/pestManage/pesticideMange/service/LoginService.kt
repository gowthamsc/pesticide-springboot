package com.pestManage.pesticideMange.service

import javax.servlet.http.HttpServletResponse

interface LoginService {
    fun login(email: String, password: String, response: HttpServletResponse): Any
    fun forgetPassword(email: String): String
    fun logout(email: String): String
}