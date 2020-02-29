package com.leosimas.udsagenda.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.extension.getString
import com.leosimas.udsagenda.extension.gone
import com.leosimas.udsagenda.extension.toastShort
import com.leosimas.udsagenda.extension.visible
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initViewModel()
        initViews()
    }

    private fun initViews() {
        supportActionBar?.setTitle(R.string.sign_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonSignUp.setOnClickListener {
            viewModel.doSignUp(
                textName.editText?.text.toString(),
                textEmail.editText?.text.toString(),
                textPassword.editText?.text.toString(),
                textPasswordConfirm.editText?.text.toString())
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.getSignUpForm().observe(this, Observer {
            textName.error = getString(it?.nameError)
            textEmail.error = getString(it?.emailError)
            textPassword.error = getString(it?.passwordError)
            textPasswordConfirm.error = getString(it?.passwordConfirmError)
        })
        viewModel.getIsLoading().observe(this, Observer {
            if (it) viewLoading.visible() else viewLoading.gone()
        })
        viewModel.getSignUpSuccess().observe(this, Observer {
            toastShort(R.string.user_created)
            finish()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> { finish(); return true }
        }
        return super.onOptionsItemSelected(item)
    }
}
