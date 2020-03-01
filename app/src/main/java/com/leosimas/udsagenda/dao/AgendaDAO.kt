package com.leosimas.udsagenda.dao

import androidx.room.*
import com.leosimas.udsagenda.bean.Agenda

@Dao
interface AgendaDAO {

    @Query("SELECT * FROM agenda WHERE open = :open")
    fun getAll(open: Boolean): List<Agenda>

    @Delete
    fun delete(agenda: Agenda)

    @Insert
    fun insert(agenda: Agenda)

    @Update
    fun update(agenda: Agenda)

}