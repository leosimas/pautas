package com.leosimas.udsagenda.ui.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leosimas.udsagenda.service.AgendaService
import com.leosimas.udsagenda.ui.common.BaseViewModel

class ProfileViewModel(application: Application) : BaseViewModel(application) {

    private val profile = MutableLiveData<ProfileData>()
    fun getProfile() : LiveData<ProfileData> {
        AgendaService.getLoggedUser(getApplication())?.let {
            profile.value = ProfileData(it.name, it.email)
        }
        return profile
    }

    fun doLogout() {
        AgendaService.logout(getApplication())
        requestSuccess.value = true
    }

}