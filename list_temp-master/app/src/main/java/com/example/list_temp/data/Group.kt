package com.example.list_temp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "groups",
    indices = [Index("id"), Index("faculty_id")],
    foreignKeys = [
        ForeignKey(
            entity = Faculty::class,
            parentColumns = ["faculty_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Group(
    @PrimaryKey val id : UUID = UUID.randomUUID(),
    @ColumnInfo (name = "group_name")var name : String = "",
    @ColumnInfo(name = "faculty_id") var facultyID: UUID ?= null
)
