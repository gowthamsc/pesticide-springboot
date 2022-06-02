package com.pestManage.pesticideMange.controller

import com.pestManage.pesticideMange.model.WarehouseOperator
import com.pestManage.pesticideMange.model.WarehouseOperatorTemp
import com.pestManage.pesticideMange.service.WarehouseOperatorService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/warehouseoperator")
@CrossOrigin
class WarehouseOperatorController(var warehouseOperatorService: WarehouseOperatorService) {

    @PostMapping
    fun addOperator(@Valid @RequestBody warehouseOperator: WarehouseOperator): WarehouseOperator =
        this.warehouseOperatorService.addPesticideOperator(warehouseOperator)

    @GetMapping("/{email}")
    fun getOperator(@PathVariable email: String): WarehouseOperator =
        this.warehouseOperatorService.viewWarehouseOperator(email)

    @GetMapping
    fun getAllWarehouseOperator(): List<WarehouseOperator> = this.warehouseOperatorService.viewAllWarehouseOperator()

    @PutMapping("/{email}")
    fun updateWarehouseOperator(
        @PathVariable email: String,
        @RequestBody warehouseOperator: WarehouseOperatorTemp
    ): WarehouseOperator = this.warehouseOperatorService.updateWarehouseOperator(email, warehouseOperator)

    @PutMapping("/{email}/{oldPassword}/{newPassword}")
    fun changePassword(
        @PathVariable email: String,
        @PathVariable oldPassword: String,
        @PathVariable newPassword: String
    ): WarehouseOperator = this.warehouseOperatorService.changePassword(email, oldPassword, newPassword)

    @DeleteMapping("/{email}")
    fun deleteOperator(@PathVariable email: String): String =
        this.warehouseOperatorService.deleteWarehouseOperator(email)

}