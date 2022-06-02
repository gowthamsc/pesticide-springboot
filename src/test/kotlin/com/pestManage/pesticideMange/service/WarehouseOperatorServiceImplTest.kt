package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.WarehouseOperatorDAO
import com.pestManage.pesticideMange.exception.AddException
import com.pestManage.pesticideMange.exception.DeleteException
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.WarehouseOperator
import com.pestManage.pesticideMange.model.WarehouseOperatorAddress
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
internal class WarehouseOperatorServiceImplTest @Autowired constructor(
    var warehouseOperatorService: WarehouseOperatorService
) {

    @Test
    fun addPesticideOperator() {
        var warehouseOperator = WarehouseOperator("warehouseoperator@gmail.com","Warehouse Operator","161Cs157$","Test","9994501743",false,
            WarehouseOperatorAddress(10,"nkl","nkl","Tn","100001"))
        assertThat(this.warehouseOperatorService.addPesticideOperator(warehouseOperator)).isNotNull

        org.junit.jupiter.api.assertThrows<AddException>(message = "Already exists") {
            this.warehouseOperatorService.addPesticideOperator(warehouseOperator)
        }

        assertThat(warehouseOperatorService.viewWarehouseOperator(warehouseOperator.operatorEmail)).isNotNull

        assertThat(warehouseOperatorService.deleteWarehouseOperator(warehouseOperator.operatorEmail)).isEqualTo("Deleted Successfully")
    }

    @Test
    fun `should get Not Found for invalid email on find`(){
        var exception =
            org.junit.jupiter.api.assertThrows<GetException>(message = "Not found") {
                this.warehouseOperatorService.viewWarehouseOperator("warehouseoperator1@gmail.com")
            }
    }

    @Test
    fun `should et Not Found for invalid email`(){
        var exception =
            org.junit.jupiter.api.assertThrows<DeleteException>(message = "Not found") {
                this.warehouseOperatorService.deleteWarehouseOperator("warehouseoperator1@gmail.com")
            }
    }
}