package com.pestManage.pesticideMange

import com.pestManage.pesticideMange.controller.DiseaseController
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.io.File

@SpringBootApplication
class PesticideMangeApplication

fun main(args: Array<String>) {
	//File(DiseaseController.uploadDirectory).mkdir()
	runApplication<PesticideMangeApplication>(*args)
}
