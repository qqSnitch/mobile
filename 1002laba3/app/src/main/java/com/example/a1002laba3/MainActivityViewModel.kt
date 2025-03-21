package com.example.a1002laba3

import androidx.lifecycle.ViewModel

class MainActivityViewModel:ViewModel() {
    private val questionBank = listOf(
    Question(R.string.question_1,true),
    Question(R.string.question_2, true),
    Question(R.string.question_3, false),
    Question(R.string.question_4, true),
    Question(R.string.question_5, false))
    private var currentIndex =0
    var isCheter =false
    val currentQuestionAnswer:Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestion: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext(){
        isCheter =false
        currentIndex = (currentIndex +1)%questionBank.size
    }
    fun moveToPrev(){
        isCheter =false
        currentIndex = (questionBank.size +currentIndex +1)%questionBank.size
    }
}