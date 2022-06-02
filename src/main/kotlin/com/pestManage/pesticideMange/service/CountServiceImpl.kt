package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.*
import org.springframework.stereotype.Service
@Service
class CountServiceImpl(var farmerDAO: FarmerDAO,
                       var consultantDAO: ConsultantDAO,
                       var warehouseOperatorDAO: WarehouseOperatorDAO,
                       var deliverPersonDAO: DeliverPersonDAO,
                       var pesticideDAO: PesticideDAO,
                       var orderDAO: OrderDAO,
                       var diseaseDAO: DiseaseDAO

                       ):CountService {
    override fun diseaseCount(): Long {
        if(diseaseDAO.count()>0)
            return diseaseDAO.count()
        else
            return 0
    }

    override fun diseaseCountByFarmer(email: String): Long {
       if(farmerDAO.existsByEmail(email))
           return diseaseDAO.countByEmail(email)
        else
            return 0
    }

    override fun diseaseCountByConsultant(email: String): Long {
        if(consultantDAO.existsByConsultantEmail(email))
            return diseaseDAO.countByConsultantEmail(email)
        else
            return 0
    }

    override fun diseaseCountByConsultantAndReviewStatus(email: String, reviewStatus: String): Long {
       if(consultantDAO.existsByConsultantEmail(email))
           return diseaseDAO.countByConsultantEmailAndReviewStatus(email,reviewStatus)
        else
            return 0
    }

    override fun countByReviewStatus(reviewStatus: String): Long {
        if(diseaseDAO.countByReviewStatus(reviewStatus)>0)
            return diseaseDAO.countByReviewStatus(reviewStatus)
        else
            return 0
    }

    override fun countByFarmerAndReviewStatus(email: String, reviewStatus: String): Long {
        if(diseaseDAO.countByEmailAndReviewStatus(email,reviewStatus)>0)
            return diseaseDAO.countByEmailAndReviewStatus(email,reviewStatus)
        else
            return 0
    }

    override fun consultantCount(): Long {
        if(consultantDAO.count()>0)
            return consultantDAO.count()
        else
            return 0
    }

    override fun warehouseOperatorCount(): Long {
        if(warehouseOperatorDAO.count()>0)
            return warehouseOperatorDAO.count()
        else
            return 0
    }

    override fun farmerCount(): Long {
        if(farmerDAO.count()>0)
            return farmerDAO.count()
        else
            return 0
    }

    override fun deliverPersonCount(): Long {
        if(deliverPersonDAO.count()>0)
            return deliverPersonDAO.count()
        else
            return 0
    }

    override fun shipmentCount(deliveryStatus: String): Long {
        if(orderDAO.countByShipmentStatus(deliveryStatus)>0)
            return orderDAO.countByShipmentStatus(deliveryStatus)
        else
            return 0
    }

    override fun pesticideCount(): Long {
        if(pesticideDAO.count()>0)
            return pesticideDAO.count()
        else
            return 0
    }

    override fun pesticideOutOfStockCount(): Long {
        if(pesticideDAO.count()>0)
            return pesticideDAO.countByAvlQtyInGm(0)
        else
            return 0
    }

    override fun countDeliverListByMail(email: String): Long {
        if(orderDAO.countByDeliverPersonEmail(email)>0)
            return orderDAO.countByDeliverPersonEmail(email)
        else
            return 0
    }

    override fun countDeliverpersonAndShipment(email: String, status: String): Long {
        if(orderDAO.countByDeliverPersonEmailAndShipmentStatus(email,status)>0){
            return orderDAO.countByDeliverPersonEmailAndShipmentStatus(email,status)
        }
        else
            return 0
    }
}