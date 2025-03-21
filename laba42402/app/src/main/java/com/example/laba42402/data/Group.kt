package com.example.laba42402.data

import java.util.UUID

class Group (
    val id: UUID = UUID.randomUUID(),
    var name: String ="",
    var facultyID: UUID?=null
)
