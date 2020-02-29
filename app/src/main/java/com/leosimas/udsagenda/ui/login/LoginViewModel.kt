package com.leosimas.udsagenda.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leosimas.udsagenda.viewmodel.BaseViewModel

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val loginForm = MutableLiveData<LoginForm>()
    fun getLoginForm() : LiveData<LoginForm> = loginForm

    fun doLogin(email: String?, password: String?) {
        // TODO
    }

}