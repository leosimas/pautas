package com.leosimas.udsagenda.ui.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import com.leosimas.udsagenda.service.AgendaService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    protected val bgScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    protected val isLoading = MutableLiveData<Boolean>(false)
    fun getIsLoading() : LiveData<Boolean> = isLoading
    protected val requestSuccess = LiveEvent<Boolean>()
    open fun getRequestSuccess() : LiveData<Boolean> = requestSuccess

    private val profile = MutableLiveData<ProfileData>()
    fun getProfile() : LiveData<ProfileData> {
        AgendaService.getLoggedUser(getApplication())?.let {
            profile.value = ProfileData(it.name, it.email)
        }
        return profile
    }

    protected fun <T> validateForm(list: Array<Validation>, form: T): T? {
        list.filter { it.isInvalid }.forEach { it.function.invoke() }
        if (list.any { it.isInvalid }) return form
        return null
    }

    protected fun requestSucceeded() {
        uiScope.launch {
            isLoading.value = false
            requestSuccess.value = true
        }
    }

}