package com.pestManage.pesticideMange.service

import com.pestManage.pesticideMange.DAO.PesticideDAO
import com.pestManage.pesticideMange.exception.AddException
import com.pestManage.pesticideMange.exception.DeleteException
import com.pestManage.pesticideMange.exception.GetException
import com.pestManage.pesticideMange.model.Pesticides
import com.pestManage.pesticideMange.model.PesticidesUpdateTemp
import org.springframework.stereotype.Service

@Service
class PesticideServiceImpl(var pesticideDAO: PesticideDAO) : PesticideService {
    override fun addPesticide(pesticide: Pesticides): Pesticides {
        if (pesticideDAO.existsByPestName(pesticide.pestName)) throw AddException("Pesticide already exists...")
        else return this.pesticideDAO.save(pesticide)
    }

    override fun updatePesticideQty(pestId: String, qty: Int): Pesticides {
        if (pesticideDAO.existsByPestId(pestId)) {
            var pesticide: Pesticides = this.viewPesticideById(pestId)
            pesticide.avlQtyInGm = qty
            return this.pesticideDAO.save(pesticide)
        } else throw GetException("Pesticide does not exists...")
    }

    override fun updatePesticidePrice(pestId: String, price: Double): Pesticides {
        if (pesticideDAO.existsByPestId(pestId)) {
            var pesticide: Pesticides = this.viewPesticideById(pestId)
            pesticide.pricePerGm = price
            return this.pesticideDAO.save(pesticide)
        } else throw GetException("Pesticide does not exists...")
    }

    override fun updatePesticide(pestId: String, pesticidesUpdateTemp: PesticidesUpdateTemp): Pesticides {
        if (pesticideDAO.existsByPestId(pestId)) {
            var pesticide: Pesticides = this.viewPesticideById(pestId)
            pesticide.pestName = pesticidesUpdateTemp.pestName
            pesticide.avlQtyInGm = pesticidesUpdateTemp.avlQtyInGm
            pesticide.pricePerGm = pesticidesUpdateTemp.pricePerGm
            return this.pesticideDAO.save(pesticide)
        } else throw GetException("Pesticide does not exists...")
    }

    override fun viewPesticideById(pesticideId: String): Pesticides {
        if (pesticideDAO.existsByPestId(pesticideId)) return this.pesticideDAO.findById(pesticideId).get()
        else throw GetException("Pesticide does not exists...")
    }

    override fun viewAllPesticides(): MutableList<Pesticides> {
        if (pesticideDAO.count() > 0) return this.pesticideDAO.findAll()
        else throw GetException("No Pesticide available...")
    }

    override fun deletePesticide(pesticideId: String): String {
        if (pesticideDAO.existsByPestId(pesticideId)) {
            this.pesticideDAO.deleteById(pesticideId)
            return "Deleted Successfully"
        } else throw DeleteException("Pesticide does not exists...")
    }

}