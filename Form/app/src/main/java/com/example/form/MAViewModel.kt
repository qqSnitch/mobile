package com.example.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MAViewModel : ViewModel() {
    val users = MutableLiveData<MutableList<User>>(mutableListOf())
    var currentIndex =0
    fun moveToNext(){
        val currentList = users.value ?: mutableListOf()
        if (currentList.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % currentList.size
        }
    }
    fun moveToPrev(){
        val currentList = users.value ?: mutableListOf()
        if (currentList.isNotEmpty()) {
            currentIndex = (currentIndex - 1 + currentList.size) % currentList.size
        }
    }
    fun NewUser(user: User){
        val currentList = users.value ?: mutableListOf()
        currentList.add(user)
        users.value = currentList
        currentIndex = currentList.size - 1
    }
    fun ChangeUser(index :Int,user: User){
        val currentList = users.value ?: mutableListOf()
        if (index >= 0 && index < currentList.size) {
            currentList[index] = user
            users.value = currentList
        }
    }
    fun getUser(): User? {
        val currentList = users.value ?: mutableListOf()
        if (currentList.isNotEmpty() && currentIndex >= 0 && currentIndex < currentList.size) {
            return currentList[currentIndex]
        }
        return null
    }
//    fun CurrentUser(): String {
//        return users[currentIndex].name
//    }
    fun cout(): String{
        return users.toString()
    }
}


//class MAViewModel : ViewModel() {
//    private val userBank = mutableListOf<User>()
//    private var currentIndex =0
//    fun moveToNext(){
//        currentIndex = (currentIndex +1)%userBank.size
//    }
//    fun moveToPrev(){
//        currentIndex = (userBank.size +currentIndex +1)%userBank.size
//    }
//    fun NewUser(name:String,name2:String,name3:String,tel:Int){
//        userBank.add(User(name,name2,name3,tel))
//    }
//    fun ChangeUser(name:String,name2:String,name3:String,tel:Int){
//        userBank[currentIndex].name=name
//        userBank[currentIndex].secName=name2
//        userBank[currentIndex].thrdName=name3
//        userBank[currentIndex].number=tel
//    }
//    fun CurrentUser(): String {
//        return userBank[currentIndex].name
//    }
//    fun cout(): String{
//        return userBank.toString()
//    }
//}