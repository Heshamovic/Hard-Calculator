package com.example.project.hardcalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class LevelsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.levels)
    }

    var currentNumber = 0
    var plusNumber = 5
    var minusNumber = 5
    var divideNumber = 5
    var multplyNumber = 5
    var InitNumber = 5
    var moves = 7
    fun ResetLevel(view: View)
    {
        currentNumber = InitNumber
        moves = 7
        display(currentNumber)
    }

    fun Plus(view: View)
    {
        moves--
        currentNumber += plusNumber
        display(currentNumber)
    }
    fun Minus(view: View)
    {
        moves--
        currentNumber -= minusNumber
        display(currentNumber)
    }
    fun Multply(view: View)
    {
        moves--
        currentNumber *= multplyNumber
        display(currentNumber)
    }
    fun Divide(view: View)
    {
        moves--
        if (currentNumber % divideNumber == 0)
            currentNumber /= divideNumber
        else
            currentNumber = currentNumber
        display(currentNumber)
    }
    fun ChangeSign(view: View)
    {
        moves--
        currentNumber *= -1
        display(currentNumber)
    }

    private fun display(number: Int) {
        val currentNumberTextView = findViewById<View>(R.id.Number_text_view) as TextView
        currentNumberTextView.text = "" + number
        if (moves < 0)
        {
            moves = 0
        }
        val currentMoves = findViewById<View>(R.id.LeftMoves_text_view) as TextView
        currentMoves.text = "Left Moves : " + moves
    }
}
