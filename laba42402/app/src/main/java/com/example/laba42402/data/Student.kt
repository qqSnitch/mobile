package com.example.laba42402.data

import java.util.Date
import java.util.UUID

class Student (
    val id : UUID = UUID.randomUUID(),
    var lastName: String="",
    var firstName: String="",
    var middleName: String="",
    var  birthDate: Date = Date(),
    var groupId:UUID? =null,
    var phone: String=" ",
    var sex:Int=0
    ){
    val shortName
        get()=lastName+
                (if(firstName.length>0){"${firstName.subSequence(0,1)}. "}else "")+
                (if(middleName.length>0){"${middleName.subSequence(0,1)}. "}else "")
}