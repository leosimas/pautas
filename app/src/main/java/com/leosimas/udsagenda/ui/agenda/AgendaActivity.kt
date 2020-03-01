package com.leosimas.udsagenda.ui.agenda

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.extension.enableWhenAllFilled
import com.leosimas.udsagenda.extension.getString
import kotlinx.android.synthetic.main.activity_agenda.*

class AgendaActivity : AppCompatActivity() {

    private lateinit var viewModel: AgendaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        buttonFinish.isEnabled = false
        buttonFinish.enableWhenAllFilled(
            textTitle.editText,
            textDescription.editText,
            textDetails.editText,
            textAuthor.editText
        )

        buttonFinish.setOnClickListener {
            viewModel.createAgenda(
                textTitle.editText?.text.toString(),
                textDescription.editText?.text.toString(),
                textDetails.editText?.text.toString(),
                textAuthor.editText?.text.toString()
            )
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AgendaViewModel::class.java)
        viewModel.getAgendaForm().observe(this, Observer {
            textTitle.error = getString(it?.titleError)
            textDescription.error = getString(it?.descriptionError)
            textDetails.error = getString(it?.detailsError)
            textAuthor.error = getString(it?.authorError)
        })
        viewModel.getProfile().observe(this, Observer {
            textAuthor.editText?.setText(it.name)
        })
        viewModel.getRequestSuccess().observe(this, Observer{
            setResult(Activity.RESULT_OK)
            finish()
        })
    }
}
