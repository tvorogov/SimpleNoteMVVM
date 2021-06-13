package com.tvorogov.simplenotemvvm.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


import java.text.DateFormat

@Entity(tableName = "note_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val pinned: Boolean = false,
    val lastEdited: Long = System.currentTimeMillis()
) : Parcelable {

    val lastEditedDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(lastEdited)

}