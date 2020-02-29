package com.leosimas.udsagenda.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.leosimas.udsagenda.bean.Agenda

@Dao
interface AgendaDAO {

    @Query("SELECT * FROM agenda WHERE open = :open")
    fun getAll(open: Boolean): List<Agenda>

    @Delete
    fun delete(agenda: Agenda)

    @Insert
    fun insert(agenda: Agenda)

}