package com.example.calculator

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToLong

class Main : AppCompatActivity() {


    private val UNDEFINED_ANS = Double.MAX_VALUE - 1


    private val inputBtnIDs: HashMap<Int, Char> = hashMapOf(
        R.id.btn_0 to '0',
        R.id.btn_1 to '1',
        R.id.btn_2 to '2',
        R.id.btn_3 to '3',
        R.id.btn_4 to '4',
        R.id.btn_5 to '5',
        R.id.btn_6 to '6',
        R.id.btn_7 to '7',
        R.id.btn_8 to '8',
        R.id.btn_9 to '9',
        R.id.btn_dot to '.',
        R.id.btn_plus to '+',
        R.id.btn_minus to '-',
        R.id.btn_mult to '*',
        R.id.btn_div to '/',
        R.id.btn_open_br to '(',
        R.id.btn_close_br to ')'
    )

    private var input: StringBuilder = StringBuilder()
    private var output: Double = UNDEFINED_ANS


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)


        val inputView: TextView = findViewById(R.id.input)
        val outputView: TextView = findViewById(R.id.output)

        val bsBtn: Button = findViewById(R.id.btn_backspace)
        val clrBtn: Button = findViewById(R.id.btn_clear)
        val calcBtn: Button = findViewById(R.id.btn_calc)
        val insResBtn: Button = findViewById(R.id.btn_ins_result)


        for ((id, ch) in inputBtnIDs) {
            val btn: Button = findViewById(id)
            btn.setOnClickListener{
                input.append(ch)
                updateIOView(inputView, outputView)
            }
        }

        bsBtn.setOnClickListener{
            if (input.isNotEmpty()) {
                input.deleteCharAt(input.length - 1)
                updateIOView(inputView, outputView)
            }
        }

        clrBtn.setOnClickListener{
            input = StringBuilder()
            updateIOView(inputView, outputView)
        }

        calcBtn.setOnClickListener{
            outputView.setTextColor(Color.BLACK)
            try {
                val result = Parser(input).parse()
                val resStr = doubleToString(result)
                outputView.text = "=$resStr"
                output = result
            } catch (e: CalcException) {
                outputView.text = e.message
            }
        }

        insResBtn.setOnClickListener{
            if (output != UNDEFINED_ANS) {
                input.append(doubleToString(output))
                updateIOView(inputView, outputView)
            }
        }

    }



    private val IN_KEY = "inKey"
    private val OUT_KEY = "outKey"

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(IN_KEY, input.toString())
        outState.putDouble(OUT_KEY, output)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        input = StringBuilder(savedInstanceState.getString(IN_KEY) ?: "")
        output = savedInstanceState.getDouble(OUT_KEY)
        updateIOView(findViewById(R.id.input), findViewById(R.id.output))
    }



    private fun updateIOView(inputView: TextView, outputView: TextView) {
        inputView.text = input.toString()
        outputView.setTextColor(Color.LTGRAY)
    }



    private val ROUND = 1_000_000_000.0

    private fun doubleToString(num: Double): String {
        if (num - num.toLong() == 0.0)
            return num.toLong().toString()
    return ((num * ROUND).roundToLong() / ROUND).toString()
    }

}
