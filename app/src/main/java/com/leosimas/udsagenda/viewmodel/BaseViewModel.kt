package com.leosimas.udsagenda.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.leosimas.udsagenda.dao.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private var dbInstance: AppDatabase? = null
    }

    private val db : AppDatabase
        get() {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(getApplication(),
                    AppDatabase::class.java, "agenda")
                    .build()
            }
            return dbInstance!!
        }

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


}