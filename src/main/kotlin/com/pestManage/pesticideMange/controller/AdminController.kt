package com.pestManage.pesticideMange.controller


import com.pestManage.pesticideMange.model.Admin
import com.pestManage.pesticideMange.model.AdminTemp
import com.pestManage.pesticideMange.service.AdminService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/admin")
@CrossOrigin
class AdminController(var adminService: AdminService) {

    @PostMapping
    fun addAdmin(@Valid @RequestBody adminTemp: AdminTemp): Admin = this.adminService.addAdmin(adminTemp)

    @GetMapping("/{email}")
    fun getAdmin(@PathVariable email: String): Admin {
        return this.adminService.viewAdmin(email)
    }

    @PutMapping("/{email}")
    fun updateAdmin(@PathVariable email: String, @RequestBody adminTemp: AdminTemp): Admin =
        this.adminService.updateAdmin(email, adminTemp)

    @PutMapping("/changepassword/{email}/{oldpassword}/{newpassword}")
    fun changePassword(
        @PathVariable email: String, @PathVariable oldpassword: String, @PathVariable newpassword: String
    ): String = this.adminService.chnagePassword(email, oldpassword, newpassword)


    @DeleteMapping("/{email}")
    fun deleteAdmin(@PathVariable email: String): String = this.adminService.deleteAdmin(email)
}