package com.leosimas.udsagenda.ui.recoverpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.extension.*
import kotlinx.android.synthetic.main.activity_recover_password.*

class RecoverActivity : AppCompatActivity() {

    private lateinit var viewModel: RecoverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)
        initViewModel()
        initViews()
    }

    private fun initViews() {
        supportActionBar?.setTitle(R.string.recover_password)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonRecover.setOnClickListener {
            hideKeyboard(textEmail)
            viewModel.doRecover(textEmail.editText?.text.toString())
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(RecoverViewModel::class.java)
        viewModel.getRecoverForm().observe(this, Observer {
            textEmail.error = getString(it?.emailError)
        })
        viewModel.getIsLoading().observe(this, Observer {
            if (it) viewLoading.visible() else viewLoading.gone()
        })
        viewModel.getRequestSuccess().observe(this, Observer {
            if (it) showSuccessDialog()
        })
    }

    private fun showSuccessDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.recover_password)
            .setMessage(R.string.msg_recover_check_email)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss(); finish() }
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> { finish(); return true }
        }
        return super.onOptionsItemSelected(item)
    }
}
