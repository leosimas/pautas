package com.leosimas.udsagenda.ui.agendalist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.ui.agenda.AgendaActivity
import com.leosimas.udsagenda.ui.login.LoginActivity
import com.leosimas.udsagenda.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_agenda_list.*

class AgendaListActivity : AppCompatActivity() {

    companion object {

        private const val REQUEST_PROFILE = 123
        private const val REQUEST_AGENDA = 234
    }

    private lateinit var pagerAdapter: AgendaListPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda_list)
        initViews()
    }

    private fun initViews() {
        pagerAdapter = AgendaListPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewPager)
        fab.setOnClickListener { showAgendaForm() }

        buttonProfile.setOnClickListener { showProfile() }
    }

    private fun showAgendaForm() {
        startActivityForResult(Intent(this, AgendaActivity::class.java),
            REQUEST_AGENDA)
    }

    private fun showProfile() {
        startActivityForResult(Intent(this, ProfileActivity::class.java),
            REQUEST_PROFILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PROFILE && resultCode == Activity.RESULT_OK) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else if (requestCode == REQUEST_AGENDA && resultCode == Activity.RESULT_OK) {
            AgendaListReceiver.sendRefresh(this)
        }
    }
}