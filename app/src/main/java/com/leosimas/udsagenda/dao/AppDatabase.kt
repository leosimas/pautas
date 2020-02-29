package com.leosimas.udsagenda.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leosimas.udsagenda.bean.Agenda

@Database(entities = [Agenda::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun agendaDAO(): AgendaDAO
}