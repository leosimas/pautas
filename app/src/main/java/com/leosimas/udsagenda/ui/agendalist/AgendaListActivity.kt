package com.leosimas.udsagenda.ui.agendalist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.ui.login.LoginActivity
import com.leosimas.udsagenda.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_agenda_list.*

class AgendaListActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_PROFILE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda_list)
        initViews()
    }

    private fun initViews() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
        fab.setOnClickListener { _ ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }

        buttonProfile.setOnClickListener { showProfile() }
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
        }
    }
}