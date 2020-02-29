package com.leosimas.udsagenda.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.bean.User
import com.leosimas.udsagenda.extension.isValidEmail
import com.leosimas.udsagenda.service.AgendaService
import com.leosimas.udsagenda.service.ErrorCode
import com.leosimas.udsagenda.ui.common.BaseViewModel
import com.leosimas.udsagenda.ui.common.Validation
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val loginForm = MutableLiveData<LoginForm?>()
    fun getLoginForm() : LiveData<LoginForm?> = loginForm

//    override fun getRequestSuccess(): LiveData<Boolean> {
//        bgScope.launch {
//            AgendaService.getLoggedUser(getApplication())?.let {
//                requestSucceeded()
//            }
//        }
//        return super.getRequestSuccess()
//    }

    private fun validateLogin(email: String?, password: String?) : LoginForm? {
        val form = LoginForm()
        val list = arrayOf(
            Validation(email.isNullOrBlank()) { form.emailError = R.string.email_required },
            Validation(password.isNullOrBlank()) { form.passwordError = R.string.password_required },
            Validation(!email.isNullOrBlank() && !email.isValidEmail()) {
                form.emailError = R.string.invalid_email }
        )
        return validateForm(list, form)
    }

    fun getLoggedUser() : User? = AgendaService.getLoggedUser(getApplication())

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

            val form = LoginForm()
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