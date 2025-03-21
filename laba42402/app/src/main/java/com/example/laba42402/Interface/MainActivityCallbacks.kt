package com.example.laba42402.Interface

import com.example.laba42402.data.Student

interface MainActivityCallbacks {
    fun newTitle(_title:String)

    fun showFragment(fragmentType: NamesOfFragment,student: Student?=null)
}