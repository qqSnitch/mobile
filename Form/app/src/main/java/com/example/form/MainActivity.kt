package com.example.form

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: MAViewModel
    private lateinit var userText: TextView
//    private val userViewModel: MAViewModel by lazy{
//        val provider = ViewModelProvider(this)
//        provider.get(MAViewModel::class.java)
//    }
    private lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editActivityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userViewModel = ViewModelProvider(this).get(MAViewModel::class.java)
        val userText : TextView = findViewById(R.id.tvUser)
        val btnAdd : Button =findViewById(R. id.btnAdd)
        val btnChange : Button =findViewById(R. id.btnChange)
        val prevButton : ImageButton =findViewById(R. id.ibPrev)
        val nextButton : ImageButton =findViewById(R. id.ibNext)
        btnAdd.setOnClickListener(){
            val intent = Intent(this, UserAddActivity::class.java)
            addActivityResultLauncher.launch(intent)
        }
        btnChange.setOnClickListener(){
            val currentUser = userViewModel.getUser()
            if (currentUser == null) {
                return@setOnClickListener
            }
            val intent = Intent(this, UserAddActivity::class.java).apply {
                putExtra("USER_INDEX", userViewModel.currentIndex)
                putExtra("USER_NAME", currentUser.name)
                putExtra("USER_NAME2", currentUser.secName)
                putExtra("USER_NAME3", currentUser.thrdName)
                putExtra("USER_PHONE", currentUser.number)
            }
            editActivityResultLauncher.launch(intent)
        }
        nextButton.setOnClickListener{
            userViewModel.moveToNext()
            displayCurrentUser()
        }
        prevButton.setOnClickListener{
            userViewModel.moveToPrev()
            displayCurrentUser()
        }
        displayCurrentUser()
    }
    private fun displayCurrentUser() {
        val user = userViewModel.getUser()
        if (user != null) {
            userText.text = "${user.name}"
        } else {
            userText.text = "Нет контактов"
        }
    }
    private fun initActivityResultLaunchers() {
        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val name = data?.getStringExtra("USER_NAME") ?: ""
                val lastname = data?.getStringExtra("USER_LASTNAME") ?: ""
                val thirdname = data?.getStringExtra("USER_THIRDNAME") ?: ""
                val phone = data?.getStringExtra("USER_PHONE") ?: ""
                userViewModel.NewUser(User(name, lastname,thirdname, phone))
            }
        }

        editActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val userIndex = data?.getIntExtra("USER_INDEX", -1) ?: -1
                val name = data?.getStringExtra("USER_NAME") ?: ""
                val lastname = data?.getStringExtra("USER_LASTNAME") ?: ""
                val thirdname = data?.getStringExtra("USER_THIRDNAME") ?: ""
                val phone = data?.getStringExtra("USER_PHONE") ?: ""
                if (userIndex != -1) {
                    userViewModel.ChangeUser(userIndex, User(name, lastname,thirdname, phone))
                }
            }
        }
    }

}