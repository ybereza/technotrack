package ru.mail.technotrack.roomsample.library

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Author(
    @PrimaryKey val id : Int,
    val name : String
)

@Entity(foreignKeys = [ForeignKey(entity = Author::class,
        parentColumns = ["id"],
        childColumns = ["authorId"],
        onDelete = CASCADE)])
data class Book (
    @PrimaryKey val id : Int,
    val title : String,
    val authorId: Int,
    val genre : String,
    val published : Date
)