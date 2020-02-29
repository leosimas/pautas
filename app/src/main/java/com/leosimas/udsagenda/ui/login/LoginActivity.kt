package com.leosimas.udsagenda.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.extension.getString
import com.leosimas.udsagenda.extension.gone
import com.leosimas.udsagenda.extension.hideKeyboard
import com.leosimas.udsagenda.extension.visible
import com.leosimas.udsagenda.ui.agendalist.AgendaListActivity
import com.leosimas.udsagenda.ui.recoverpassword.RecoverActivity
import com.leosimas.udsagenda.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViewModel()
        initViews()
    }

    private fun initViews() {
        buttonLogin.setOnClickListener {
            hideKeyboard(textPassword)
            viewModel.doLogin(textEmail.editText?.text.toString(),
                textPassword.editText?.text.toString())
        }
        buttonSignUp.setOnClickListener { showSignUp() }
        buttonRecoverPassword.setOnClickListener { showRecoverPassword() }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.getLoggedUser()?.let {
            showAgendaList()
        }
        viewModel.getLoginForm().observe(this, Observer {
            textEmail.error = getString(it?.emailError)
            textPassword.error = getString(it?.passwordError)
        })
        viewModel.getIsLoading().observe(this, Observer {
            if (it) viewLoading.visible() else viewLoading.gone()
        })
        viewModel.getRequestSuccess().observe(this, Observer {
            showAgendaList()
        })
    }

    private fun showAgendaList() {
        startActivity(Intent(this, AgendaListActivity::class.java))
        finish()
    }

    private fun showSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun showRecoverPassword() {
        startActivity(Intent(this, RecoverActivity::class.java))
    }
}
