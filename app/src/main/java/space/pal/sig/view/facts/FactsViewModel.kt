package space.pal.sig.view.facts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import space.pal.sig.model.dto.FactDto
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

    private val facts = MediatorLiveData<List<FactDto>>()

    init {
        facts.addSource(factRepository.findAll()) { facts ->
            this.facts.value = facts.mapIndexed { index, fact ->
                FactDto(fact.content, "#${index + 1}")
            }.shuffled()
        }
    }

    fun getFacts(): LiveData<List<FactDto>> {
        return facts
    }

}