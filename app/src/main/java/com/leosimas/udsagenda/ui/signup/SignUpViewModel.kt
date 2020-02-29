package com.leosimas.udsagenda.ui.signup

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.extension.isValidEmail
import com.leosimas.udsagenda.service.AgendaService
import com.leosimas.udsagenda.service.ErrorCode
import com.leosimas.udsagenda.ui.common.Validation
import com.leosimas.udsagenda.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : BaseViewModel(application) {

    private val signupForm = MutableLiveData<SignUpForm?>()
    fun getSignUpForm() : LiveData<SignUpForm?> = signupForm
    private val signUpSuccess = MutableLiveData<Boolean>()
    fun getSignUpSuccess() : LiveData<Boolean> = signUpSuccess

    private fun validateSignUp(name: String?,email: String?,
                               password: String?, passwordConfirm: String?) : SignUpForm? {
        val form = SignUpForm()
        val list = arrayOf(
            Validation(name.isNullOrBlank()) { form.nameError = R.string.name_required },
            Validation(email.isNullOrBlank()) { form.emailError = R.string.email_required },
            Validation(password.isNullOrBlank()) { form.passwordError = R.string.password_required },
            Validation(passwordConfirm.isNullOrBlank()) {
                form.passwordConfirmError = R.string.password_required },
            Validation(!email.isNullOrBlank() && !email.isValidEmail()) {
                form.emailError = R.string.invalid_email },
            Validation(!password.isNullOrBlank() && !passwordConfirm.isNullOrBlank()
                    && password != passwordConfirm) {
                form.passwordConfirmError = R.string.passwords_dont_match }
        )
        return validateForm(list, form)
    }

    fun doSignUp(name: String?,email: String?,
                 password: String?, passwordConfirm: String?) {
        signupForm.value = validateSignUp(name, email, password, passwordConfirm)
        if (signupForm.value != null) return

        isLoading.value = true

        bgScope.launch {
            val res = AgendaService.signUp(getApplication(), name!!, email!!, password!!)
            if (res.success) {
                loginSucceeded()
                return@launch
            }

            val form = SignUpForm().apply {
                emailError = R.string.email_already_used
            }

            uiScope.launch {
                isLoading.value = false
                signupForm.value = form
            }
        }
    }

    private fun loginSucceeded() {
        uiScope.launch {
            isLoading.value = false
            signUpSuccess.value = true
        }
    }

}