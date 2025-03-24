package com.example.laba2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val users = MutableLiveData<MutableList<User>>(mutableListOf())
    var currentIndex: Int = 0
        private set

    fun addUser(user: User) {
        val currentList = users.value ?: mutableListOf()
        currentList.add(user)
        users.value = currentList
        currentIndex = currentList.size - 1
    }

    fun editUser(index: Int, user: User) {
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

    fun nextUser() {
        val currentList = users.value ?: mutableListOf()
        if (currentList.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % currentList.size
        }
    }

    fun previousUser() {
        val currentList = users.value ?: mutableListOf()
        if (currentList.isNotEmpty()) {
            currentIndex = (currentIndex - 1 + currentList.size) % currentList.size
        }
    }
}