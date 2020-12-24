package space.pal.sig.view.facts

import android.app.Application
import androidx.lifecycle.LiveData
import space.pal.sig.model.entity.Fact
import space.pal.sig.repository.FactRepository
import space.pal.sig.view.BaseViewModel

/**
 * SpaceNow
 * Created by Catalin on 12/24/2020
 **/
class FactsViewModel(
        application: Application,
        factRepository: FactRepository
) : BaseViewModel(application) {

    private val facts = factRepository.findAll()

    fun getFacts(): LiveData<MutableList<Fact>> {
        return facts
    }

}