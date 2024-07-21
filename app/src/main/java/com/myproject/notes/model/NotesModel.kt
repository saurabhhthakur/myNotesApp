package com.myproject.notes.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class NotesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val note: String,
    val date: String
) : Parcelable