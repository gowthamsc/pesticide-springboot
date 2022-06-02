package com.pestManage.pesticideMange.model

import org.springframework.web.multipart.MultipartFile

data class DiseaseImageDTO (
    var email:String,
    var diseaseDescription:String,
    var acre:Int,
    var diseaseImage:MultipartFile,
        )