package com.leosimas.udsagenda.ui.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.leosimas.udsagenda.dao.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    protected val bgScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    protected val isLoading = MutableLiveData<Boolean>(false)
    fun getIsLoading() : LiveData<Boolean> = isLoading

    protected fun <T> validateForm(list: Array<Validation>, form: T): T? {
        list.filter { it.isInvalid }.forEach { it.function.invoke() }
        if (list.any { it.isInvalid }) return form
        return null
    }

}