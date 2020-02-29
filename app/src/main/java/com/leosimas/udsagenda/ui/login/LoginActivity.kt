package com.leosimas.udsagenda.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.extension.getString
import com.leosimas.udsagenda.extension.gone
import com.leosimas.udsagenda.extension.visible
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
}
