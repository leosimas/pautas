package com.leosimas.udsagenda.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.leosimas.udsagenda.bean.User

@Dao
interface UserDAO {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE email = :email")
    fun findByEmail(email: String) : User?

}