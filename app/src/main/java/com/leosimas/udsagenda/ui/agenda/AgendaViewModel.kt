package com.leosimas.udsagenda.ui.agenda

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.service.AgendaService
import com.leosimas.udsagenda.ui.common.BaseViewModel
import com.leosimas.udsagenda.ui.common.Validation
import kotlinx.coroutines.launch

class AgendaViewModel(application: Application) : BaseViewModel(application) {

    private val agendaForm = MutableLiveData<AgendaForm?>()
    fun getAgendaForm() : LiveData<AgendaForm?> = agendaForm

    private fun validateAgenda(title: String?, description: String?,
                               details: String?, author: String?) : AgendaForm? {
        val form = AgendaForm()
        val list = arrayOf(
            Validation(title.isNullOrBlank()) { form.titleError = R.string.title_required },
            Validation(description.isNullOrBlank()) { form.descriptionError = R.string.description_required },
            Validation(details.isNullOrBlank()) { form.detailsError = R.string.details_required },
            Validation(author.isNullOrBlank()) { form.authorError = R.string.author_required }
        )
        return validateForm(list, form)
    }

    fun createAgenda(
        title: String?,
        description: String?,
        details: String?,
        author: String?
    ) {
        agendaForm.value = validateAgenda(title, description, details, author)
        if (agendaForm.value != null) return

        isLoading.value = true

        bgScope.launch {
            val res = AgendaService.createAgenda(getApplication(),
                title!!, description!!, details!!, author!!)
            if (res.success) {
                requestSucceeded()
                return@launch
            }
        }
    }

}
