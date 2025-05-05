package com.example.form

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import android.text.Editable
import android.text.TextWatcher

class UserAddActivity : AppCompatActivity() {
    private lateinit var viewModel: MAViewModel
    private var userIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_add)

        viewModel = ViewModelProvider(this).get(MAViewModel::class.java)


        val name1: EditText = findViewById(R.id.etName)
        val name2: EditText = findViewById(R.id.etName2)
        val name3: EditText = findViewById(R.id.etName3)
        val tel: EditText = findViewById(R.id.etTel)
        val savebtn: Button = findViewById(R.id.btnSave)


        userIndex = intent.getIntExtra("USER_INDEX", -1)


        if (userIndex != -1) {
            val userName = intent.getStringExtra("USER_NAME") ?: ""
            val userLastName = intent.getStringExtra("USER_NAME1") ?: ""
            val userThrdName = intent.getStringExtra("USER_NAME2") ?: ""
            val userPhone = intent.getStringExtra("USER_PHONE") ?: ""
            name1.setText(userName)
            name2.setText(userLastName)
            name3.setText(userThrdName)
            tel.setText(userPhone)
        }


        tel.addTextChangedListener(object : TextWatcher {
            private var isEditing = false

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (isEditing) return
                isEditing = true

                val formatted = formatPhoneNumber(s.toString())
                tel.setText(formatted)
                tel.setSelection(formatted.length)

                isEditing = false
            }
        })

        savebtn.setOnClickListener {
            val name = name1.text.toString().trim()
            val lastname = name2.text.toString().trim()
            val thrdname = name3.text.toString().trim()
            val phone = tel.text.toString().trim()


            val resultIntent = Intent()
            resultIntent.putExtra("USER_NAME", name)
            resultIntent.putExtra("USER_NAME2", lastname)
            resultIntent.putExtra("USER_NAME2", thrdname)
            resultIntent.putExtra("USER_PHONE", phone)
            resultIntent.putExtra("USER_INDEX", userIndex)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun formatPhoneNumber(input: String): String {
        val numbers = input.replace(Regex("[^\\d]"), "")

        return when {
            numbers.length >= 11 -> {
                "+7 (${numbers.substring(1, 4)}) ${numbers.substring(4, 7)} ${numbers.substring(7, 9)}-${numbers.substring(9, 11)}"
            }
            numbers.length == 10 -> {
                "(${numbers.substring(0, 3)}) ${numbers.substring(3, 6)} ${numbers.substring(6, 8)}-${numbers.substring(8, 10)}"
            }
            numbers.length > 1 -> {
                "+${numbers.substring(0, 1)} (${numbers.substring(1)})"
            }
            else -> input
        }
    }
}

//class UserAddActivity : AppCompatActivity() {
//    private val userViewModel: MAViewModel by lazy{
//        val provider = ViewModelProvider(this)
//        provider.get(MAViewModel::class.java)
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_user_add)
//        val name : EditText =findViewById(R. id.etName)
//        val name2 : EditText =findViewById(R. id.etName2)
//        val name3 : EditText =findViewById(R. id.etName3)
//        val tel : EditText =findViewById(R. id.etTel)
//        val btnSave : Button =findViewById(R. id.btnSave)
//        btnSave.setOnClickListener(){
//            userViewModel.NewUser(name.text.toString(),name2.text.toString(),name3.text.toString(),tel.text.toString().toInt())
//            Log.i("Test",userViewModel.cout())
//            finish()
//        }
//    }
//}