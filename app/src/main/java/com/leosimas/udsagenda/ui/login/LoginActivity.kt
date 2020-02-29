package com.leosimas.udsagenda.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.extension.getString
import com.leosimas.udsagenda.extension.gone
import com.leosimas.udsagenda.extension.visible
import com.leosimas.udsagenda.ui.signup.SignUpActivity
import com.leosimas.udsagenda.ui.signup.SignUpViewModel
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
            viewModel.doLogin(textEmail.editText?.text.toString(),
                textPassword.editText?.text.toString())
        }
        buttonSignUp.setOnClickListener { showSignUp() }
        buttonRecoverPassword.setOnClickListener { showRecoverPassword() }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.getLoginForm().observe(this, Observer {
            textEmail.error = getString(it?.emailError)
            textPassword.error = getString(it?.passwordError)
        })
        viewModel.getIsLoading().observe(this, Observer {
            if (it) viewLoading.visible() else viewLoading.gone()
        })
        viewModel.getLoginSuccess().observe(this, Observer {
            // TODO go to Agenda List activity
        })
    }

    private fun showSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun showRecoverPassword() {
        // TODO
    }
}
