package com.example.a1002laba3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.time.Instant

class MainActivity : AppCompatActivity() {
    private lateinit var btnTrue:Button
    private lateinit var btnFalse:Button
    private lateinit var nextButton:Button
    private lateinit var prevButton:Button
    private lateinit var questionTextView:TextView

    private val quizViewModel: MainActivityViewModel by lazy{
        val provider =ViewModelProvider(this)
        provider.get(MainActivityViewModel::class.java)
    }
    override fun onCreate (savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTrue=findViewById(R.id.btnTrue)
        btnFalse=findViewById(R.id.btnFalse)
        nextButton=findViewById(R.id.ibNext)
        prevButton=findViewById(R.id.ibPrev)
        questionTextView=findViewById(R.id.tvQuestions)

        updateQuestion()

        btnTrue.setOnClickListener{
            checkAnswer(true)
        }
        btnTrue.setOnClickListener{
            checkAnswer(flase)
        }
        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        nextButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data:Intent? = result.data
                quizViewModel.isCheter =data?.getBooleanExtra(EXTRA_ANSWER_SHOWN,false)?:false
            }
        }
        findViewById<Button>(R.id.btnCheat).setOnClickListener{
            val intent = Intent(this,CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, quizViewModel.currentQuestionAnswer)
            startActivity(intent)
        }
    }
    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private  fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val isCheat=quizViewModel.isCheater
        val messageResId=when{
            isCheat->"Not good"
            userAnswer ==correctAnswer ->"Correct"
            else -> "Lie"
        }
        Toast.makeText(this,messageResId,Toast.LENGHT_SHORT).show()
    }
}