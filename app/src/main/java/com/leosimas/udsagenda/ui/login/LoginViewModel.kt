package com.leosimas.udsagenda.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.extension.isValidEmail
import com.leosimas.udsagenda.service.AgendaService
import com.leosimas.udsagenda.service.ErrorCode
import com.leosimas.udsagenda.ui.common.Validation
import com.leosimas.udsagenda.ui.common.BaseViewModel
import com.leosimas.udsagenda.ui.signup.SignUpForm
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val loginForm = MutableLiveData<SignUpForm?>()
    fun getLoginForm() : LiveData<SignUpForm?> = loginForm

    private fun validateLogin(email: String?, password: String?) : SignUpForm? {
        val form = SignUpForm()
        val list = arrayOf(
            Validation(email.isNullOrBlank()) { form.emailError = R.string.email_required },
            Validation(password.isNullOrBlank()) { form.passwordError = R.string.password_required },
            Validation(!email.isNullOrBlank() && !email.isValidEmail()) {
                form.emailError = R.string.invalid_email }
        )
        return validateForm(list, form)
    }

    fun doLogin(email: String?, password: String?) {
        loginForm.value = validateLogin(email, password)
        if (loginForm.value != null) return

        isLoading.value = true

        bgScope.launch {
            val res = AgendaService.login(getApplication(), email!!, password!!)
            if (res.success) {
                requestSucceeded()
                return@launch
            }

            val form = SignUpForm()
            when(res.error) {
                ErrorCode.WRONG_PASSWORD -> form.passwordError = R.string.wrong_password
                else -> form.emailError = R.string.email_not_found
            }

            uiScope.launch {
                isLoading.value = false
                loginForm.value = form
            }
        }
    }

}