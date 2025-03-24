package com.example.laba2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import android.text.Editable
import android.text.TextWatcher

class EditActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private var userIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        val nameInput: EditText = findViewById(R.id.nameInput)
        val famInput: EditText = findViewById(R.id.lastnameInput)
        val phoneInput: EditText = findViewById(R.id.phoneInput3)
        val saveButton: Button = findViewById(R.id.saveButton)


        userIndex = intent.getIntExtra("USER_INDEX", -1)


        if (userIndex != -1) {
            val userName = intent.getStringExtra("USER_NAME") ?: ""
            val userLastName = intent.getStringExtra("USER_LASTNAME") ?: ""
            val userPhone = intent.getStringExtra("USER_PHONE") ?: ""
            nameInput.setText(userName)
            famInput.setText(userLastName)
            phoneInput.setText(userPhone)
        }


        phoneInput.addTextChangedListener(object : TextWatcher {
            private var isEditing = false

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (isEditing) return
                isEditing = true

                val formatted = formatPhoneNumber(s.toString())
                phoneInput.setText(formatted)
                phoneInput.setSelection(formatted.length)

                isEditing = false
            }
        })

        saveButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val lastname = famInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()


            val resultIntent = Intent()
            resultIntent.putExtra("USER_NAME", name)
            resultIntent.putExtra("USER_LASTNAME", lastname)
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