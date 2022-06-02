package com.pestManage.pesticideMange.service


import com.pestManage.pesticideMange.DAO.DiseaseDAO
import com.pestManage.pesticideMange.DAO.FarmerDAO
import com.pestManage.pesticideMange.DAO.OrderDAO
import com.pestManage.pesticideMange.exception.DeleteException
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.Disease
import com.pestManage.pesticideMange.model.Farmer
import com.pestManage.pesticideMange.model.OrderHistory
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class DiseaseServiceImpl(
    var diseaseDao: DiseaseDAO,
    var farmerDao: FarmerDAO,
    var farmerService: FarmerService,
    var orderDAO: OrderDAO,
    var sendEmailServiceImpl: SendEmailServiceImpl
) : DiseaseService {


    override fun addDisease(file: MultipartFile, email: String, diseaseDescription: String, acre: Int): Farmer {
        var email: String = email
        var farmer: Farmer = farmerService.getFarmerById(email)
        var disease_Id = ObjectId().toString()
        /*var fileNames:StringBuilder=StringBuilder()
        var fileName:String=disease_Id+"." + file?.originalFilename!!.substring(file.originalFilename!!.length-4)
        var filePath:Path=Paths.get(DiseaseController.uploadDirectory,fileName)
        Files.write(filePath,file?.bytes)*/

        val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val now = LocalDateTime.now()

        var disease = Disease(
            disease_Id, email, diseaseDescription, acre, dtf.format(now), "Pending", null, null, null, null,
            Base64.getEncoder().encodeToString(file.bytes)
        )
        var order: OrderHistory = OrderHistory(disease_Id, farmer.email, "Nothing", "Pending")
        order.disease = disease
        orderDAO.save(order)
        farmer.diseease?.add(disease)
        farmerDao.save(farmer)
        this.diseaseDao.save(disease)
        var subject: String = "Pesticide Management Service Notification"
        var body: String =
            "Hi ${farmer.name},\n Your crop disease has been added and take it to review. Other information will recieved shortly. \n Your crop disease are as follows: \nDisease Description: ${disease.diseaseDescription}\nAffected area in acre: ${disease.acre}\nDate and Time of added disease: ${disease.addedDate}.\n\n Thanks,\n Pesticie Management Team"
        sendEmailServiceImpl.sendMail(farmer.email, body, subject)
        return farmer
    }

    override fun viewFarmerDisease(email: String): List<Disease> {
        if (diseaseDao.existsDiseaseByEmail(email))
            return this.diseaseDao.findDiseaseByEmail(email)
        else
            throw GetException("No disease registered..... Please initiate to add")
    }

    override fun viewConsultantDisease(email: String): List<Disease> {
        if (diseaseDao.existsDiseaseByConsultantEmail(email))
            return this.diseaseDao.findDiseaseByConsultantEmail(email)
        else
            throw GetException("No disease found..... Please initiate to take review")
    }

    override fun viewAllDisease(): MutableList<Disease> {
        if (diseaseDao.count() > 0)
            return this.diseaseDao.findAll()
        else
            throw GetException("There's no disease registered yet.")
    }


    override fun viewDiseaseByDiseaseId(diseaseId: String): Disease {

        if (diseaseDao.existsById(diseaseId))
            return this.diseaseDao.findById(diseaseId).get()
        else
            throw GetException("No disease registered..... Please initiate to add")
    }

    override fun deleteDisease(diseaseId: String): String {

        if (diseaseDao.existsById(diseaseId)) {
            this.diseaseDao.deleteById(diseaseId)
            return "Deleted Successfully"
        } else
            throw DeleteException("No disease registered..... Please initiate to add")
    }

    override fun checkByConsultant(email: String): String {
        if (diseaseDao.existsDiseaseByConsultantEmailAndReviewStatus(email, "InProgress"))
            return "Exists"
        else
            return "Not Exists"
    }

}