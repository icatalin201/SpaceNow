package space.pal.sig.repository

import androidx.lifecycle.LiveData
import space.pal.sig.database.dao.FactDao
import space.pal.sig.model.entity.Fact

/**
 * SpaceNow
 * Created by Catalin on 12/24/2020
 **/
class FactRepository(
        private val factDao: FactDao
) {

    fun save(fact: Fact) {
        return factDao.save(fact)
    }

    fun findAll(): LiveData<MutableList<Fact>> {
        return factDao.findAll()
    }

}