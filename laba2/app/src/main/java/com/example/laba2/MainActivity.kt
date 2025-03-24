package com.example.laba2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var userNameTextView: TextView

    private lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userNameTextView = findViewById(R.id.textView)
        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        val buttonEdit: Button = findViewById(R.id.buttonEdit)
        val buttonNext: ImageButton = findViewById(R.id.buttonNext)
        val buttonPrevious: ImageButton = findViewById(R.id.buttonPrevious)

        viewModel.users.observe(this, Observer {
            displayCurrentUser()
        })

        initActivityResultLaunchers()

        buttonAdd.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            addActivityResultLauncher.launch(intent)
        }

        buttonEdit.setOnClickListener {
            val currentUser = viewModel.getUser()
            if (currentUser == null) {
                return@setOnClickListener
            }
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra("USER_INDEX", viewModel.currentIndex)
                putExtra("USER_NAME", currentUser.name)
                putExtra("USER_LASTNAME", currentUser.lastname)
                putExtra("USER_PHONE", currentUser.phone)
            }
            editActivityResultLauncher.launch(intent)
        }

        buttonNext.setOnClickListener {
            viewModel.nextUser()
            displayCurrentUser()
        }

        buttonPrevious.setOnClickListener {
            viewModel.previousUser()
            displayCurrentUser()
        }

        displayCurrentUser()
    }

    private fun initActivityResultLaunchers() {
        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val name = data?.getStringExtra("USER_NAME") ?: ""
                val lastname = data?.getStringExtra("USER_LASTNAME") ?: ""
                val phone = data?.getStringExtra("USER_PHONE") ?: ""

                viewModel.addUser(User(name, lastname, phone))
            }
        }

        editActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val userIndex = data?.getIntExtra("USER_INDEX", -1) ?: -1
                val name = data?.getStringExtra("USER_NAME") ?: ""
                val lastname = data?.getStringExtra("USER_LASTNAME") ?: ""
                val phone = data?.getStringExtra("USER_PHONE") ?: ""
                if (userIndex != -1) {
                    viewModel.editUser(userIndex, User(name, lastname, phone))
                }
            }
        }
    }    private fun displayCurrentUser() {
        val user = viewModel.getUser()
        if (user != null) {
            userNameTextView.text = "${user.name} ${user.lastname}"
        } else {
            userNameTextView.text = "Нет контактов"
        }
    }
}
