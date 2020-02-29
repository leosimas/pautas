package com.leosimas.udsagenda.ui.recoverpassword

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.extension.isValidEmail
import com.leosimas.udsagenda.service.AgendaService
import com.leosimas.udsagenda.ui.common.Validation
import com.leosimas.udsagenda.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class RecoverViewModel(application: Application) : BaseViewModel(application) {

    private val recoverForm = MutableLiveData<RecoverForm?>()
    fun getRecoverForm() : LiveData<RecoverForm?> = recoverForm

    private fun validateRecover(email: String?) : RecoverForm? {
        val form = RecoverForm()
        val list = arrayOf(
            Validation(email.isNullOrBlank()) { form.emailError = R.string.email_required },
            Validation(!email.isNullOrBlank() && !email.isValidEmail()) {
                form.emailError = R.string.invalid_email }
        )
        return validateForm(list, form)
    }

    fun doRecover(email: String?) {
        recoverForm.value = validateRecover(email)
        if (recoverForm.value != null) return

        isLoading.value = true

        bgScope.launch {
            val res = AgendaService.recoverPassword(getApplication(), email!!)
            if (res.success) {
                requestSucceeded()
                return@launch
            }

            val form = RecoverForm().apply {
                emailError = R.string.email_not_found
            }

            uiScope.launch {
                isLoading.value = false
                recoverForm.value = form
            }
        }
    }

}