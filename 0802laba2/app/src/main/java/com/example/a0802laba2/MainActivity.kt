package com.example.a0802laba2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val oper : TextView =findViewById(R. id.tvOper)
        val num1 : TextView =findViewById(R. id.etNumber)
        val num2 : TextView =findViewById(R. id.tvOtvet)

        val btn0 : Button =findViewById(R. id.btn0)
        val btn1 : Button =findViewById(R. id.btn1)
        val btn2 : Button =findViewById(R. id.btn2)
        val btn3 : Button =findViewById(R. id.btn3)
        val btn4 : Button =findViewById(R. id.btn4)
        val btn5 : Button =findViewById(R. id.btn5)
        val btn6 : Button =findViewById(R. id.btn6)
        val btn7 : Button =findViewById(R. id.btn7)
        val btn8 : Button =findViewById(R. id.btn8)
        val btn9 : Button =findViewById(R. id.btn9)

        val btnFloat : Button =findViewById(R. id.btnFloat)
        val btnClear : Button =findViewById(R. id.btnAC)
        val btnOtvet : Button =findViewById(R. id.btnEquals)
        val btnSum : Button =findViewById(R. id.btnSum)
        val btnSub : Button =findViewById(R. id.btnSub)
        val btnMulti : Button =findViewById(R. id.btnMulti)
        val btnDiv : Button =findViewById(R. id.btnDiv)

        btn0.setOnClickListener { num1.append("0") }
        btn1.setOnClickListener { num1.append("1") }
        btn2.setOnClickListener { num1.append("2") }
        btn3.setOnClickListener { num1.append("3") }
        btn4.setOnClickListener { num1.append("4") }
        btn5.setOnClickListener { num1.append("5") }
        btn6.setOnClickListener { num1.append("6") }
        btn7.setOnClickListener { num1.append("7") }
        btn8.setOnClickListener { num1.append("8") }
        btn9.setOnClickListener { num1.append("9") }

        var dot:String="."
        btnClear.setOnClickListener{
            num1.text=""
            num2.text=""
            oper.text=""
            dot="."
        }

        btnFloat.setOnClickListener{
            num1.append(dot)
            dot=""
        }

        btnSum.setOnClickListener{
            if(num1.text.toString() != "")
            {
                oper.text="+"
                if(num2.text.toString() == "")
                {
                    num2.text=num1.text
                    num1.text=""
                }else
                {
                    num2.text=(num2.text.toString().toFloat()+num1.text.toString().toFloat()).toString()
                    num1.text=""
                }
                dot="."
            }
        }

        btnSub.setOnClickListener{
            if(num1.text.toString() != "")
            {
                oper.text="-"
                if(num2.text.toString() == "")
                {
                    num2.text=num1.text
                    num1.text=""
                }else
                {
                    num2.text=(num2.text.toString().toFloat()-num1.text.toString().toFloat()).toString()
                    num1.text=""
                }
                dot="."
            }
        }

        btnMulti.setOnClickListener{
            if(num1.text.toString() != "")
            {
                oper.text="*"
                if(num2.text.toString() == "")
                {
                    num2.text=num1.text
                    num1.text=""
                }else
                {
                    num2.text=(num2.text.toString().toFloat()*num1.text.toString().toFloat()).toString()
                    num1.text=""
                }
                dot="."
            }
        }

        btnDiv.setOnClickListener{
            if(num1.text.toString() != "")
            {
                oper.text="/"
                if(num1.text.toString() != "0") {
                    if (num2.text.toString() == "") {
                        num2.text = num1.text
                        num1.text = ""
                    } else {
                        num2.text = (num2.text.toString().toFloat() / num1.text.toString().toFloat()).toString()
                        num1.text = ""
                    }
                }
                dot="."
            }
        }

        btnOtvet.setOnClickListener{
            if(num1.text.toString() !="")
            {
                if(num2.text.toString() != ""){
                    if(oper.text.toString() !="")
                    {
                        if(num1.text.toString() == "0"  && oper.text.toString() =="/"){
                            num2.text="Делить на 0 нельзя!"
                        }else{
                            val n1=num1.text.toString().toFloat()
                            val n2=num2.text.toString().toFloat()
                            num2.text = when (oper.text){
                                "*"->{
                                    (n1*n2).toString()
                                }
                                "/"->{
                                    (n2/n1).toString()
                                }
                                "+"->{
                                    (n1+n2).toString()
                                }
                                "-"->{
                                    (n1-n2).toString()
                                }
                                else ->""
                            }
                            num1.text=""
                            oper.text=""
                        }
                    }
                }
            }
        }
    }
}
