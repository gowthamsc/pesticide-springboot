package com.pestManage.pesticideMange.controller

import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.*
import com.pestManage.pesticideMange.service.*
import com.pestManage.pesticideMange.util.JwtUtils
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin
class LoginController(
    var loginService: LoginService,
    var farmerService: FarmerService,
    var adminService: AdminService,
    var consultantService: ConsultantService,
    var warehouseOperatorService: WarehouseOperatorService,
    var deliverPersonService: DeliverPersonService,
    var util: JwtUtils

) {
    private  val jwtNull = "Email and password Not Found"
    private val invalidType = "Inavlid type"

    @PutMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO, response: HttpServletResponse): Any {
        return this.loginService.login(loginDTO.email, loginDTO.password, response)
    }


    @PutMapping("/logout")
    fun logout(@RequestBody logoutDTO: LogoutDTO, response: HttpServletResponse): String {

        val cookie = Cookie(logoutDTO.type, "")
        cookie.maxAge = 0
        response.addCookie(cookie)

        return this.loginService.logout(logoutDTO.email)
    }

    @GetMapping("/forgetpassword/{email}")
    fun forgetPassword(@PathVariable email: String): String {
        return this.loginService.forgetPassword(email)
    }
}