package com.leosimas.udsagenda.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leosimas.udsagenda.bean.Agenda
import com.leosimas.udsagenda.bean.User

@Database(entities = [Agenda::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun agendaDAO(): AgendaDAO
    abstract fun userDAO(): UserDAO
}