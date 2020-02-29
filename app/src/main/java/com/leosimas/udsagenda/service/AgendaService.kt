package com.leosimas.udsagenda.service

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import com.leosimas.udsagenda.bean.User
import com.leosimas.udsagenda.dao.AppDatabase
import com.leosimas.udsagenda.dao.SharedPref

object AgendaService {

    private var loggedUser : User? = null

    private var dbInstance: AppDatabase? = null

    private fun getDB(context: Context) : AppDatabase {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "uds_agenda")
                .build()
        }
        return dbInstance!!
    }

    private fun fakeRequestTime() {
        Thread.sleep(2000)
    }

    fun getLoggedUser(context: Context) : User? {
        if (loggedUser == null){
            loggedUser = SharedPref.loadUser(context)
        }
        return loggedUser
    }

    fun login(context: Context, email: String, password: String) : Response {
        fakeRequestTime()

        val user = getDB(context).userDAO().findByEmail(email)
            ?: return Response(false, ErrorCode.EMAIL_NOT_FOUND)

        if (user.password != password)
            return Response(false, ErrorCode.WRONG_PASSWORD)

        loggedUser = User(user.email, user.name, "")
        SharedPref.saveUser(context, user)

        return Response.SUCCESS
    }

    fun signUp(context: Context, name: String, email: String, password: String): Response {
        fakeRequestTime()

        try {
            getDB(context).userDAO().insert(User(email, name, password))
        } catch (e : SQLiteConstraintException) {
            return Response(false, ErrorCode.EMAIL_ALREADY_USED)
        }

        return Response.SUCCESS
    }

    fun recoverPassword(context: Context, email: String): Response {
        fakeRequestTime()

        getDB(context).userDAO().findByEmail(email)
            ?: return Response(false, ErrorCode.EMAIL_NOT_FOUND)

        return Response.SUCCESS
    }

    fun logout(context: Context) {
        loggedUser = null
        SharedPref.saveUser(context, null)
    }

}