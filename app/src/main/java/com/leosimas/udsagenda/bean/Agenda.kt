package com.leosimas.udsagenda.bean

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "agenda")
data class Agenda(
    @PrimaryKey val uid: Int?,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "details") var details: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "open") var open: Boolean
) : Parcelable
