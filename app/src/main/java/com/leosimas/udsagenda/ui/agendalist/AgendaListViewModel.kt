package com.leosimas.udsagenda.ui.agendalist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leosimas.udsagenda.bean.Agenda
import com.leosimas.udsagenda.service.AgendaService
import com.leosimas.udsagenda.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class AgendaListViewModel(application: Application) : BaseViewModel(application) {

    private val agendas = MutableLiveData<List<Agenda>>()
    val listAgendas: LiveData<List<Agenda>> = agendas

    private lateinit var filter: AgendaFilter

    fun setFilter(filter: AgendaFilter) {
        this.filter = filter
        refresh()
    }

    fun refresh() {
        bgScope.launch {
            val list = AgendaService.listAgendas(getApplication(), filter == AgendaFilter.OPEN)
            uiScope.launch {
                agendas.value = list
            }
        }
    }
}