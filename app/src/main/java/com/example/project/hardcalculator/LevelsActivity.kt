package com.example.project.hardcalculator

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.graphics.Color
import kotlinx.android.synthetic.main.levels.*
import kotlin.math.max

class LevelsActivity : AppCompatActivity() {

    var plusNumber = 1
    var minusNumber = 2
    var divideNumber = 3
    var multplyNumber = 4
    var initNumber = 1
    var currentNumber = initNumber
    var moves = 7
    var finalNumber = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.levels)
        plusButton.text = "+ " + plusNumber.toString()
        minusButton.text = "- " + minusNumber.toString()
        multiplyButton.text = "x " + multplyNumber.toString()
        divideButton.text = "/ " + divideNumber.toString()
        targetNumberTextView.text = "Target : " + finalNumber.toString()
        display(initNumber)
    }
    fun Reset ()
    {
        currentNumber = initNumber
        moves = 7
        divideButton.isEnabled = true
        multiplyButton.isEnabled = true
        minusButton.isEnabled = true
        plusButton.isEnabled = true
        changeSignButton.isEnabled = true
        display(currentNumber)
    }
    fun ResetLevel(view: View)
    {
        Reset()
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
    fun Multiply(view: View)
    {
        moves--
        currentNumber *= multplyNumber
        display(currentNumber)
    }
    fun Divide(view: View)
    {
        if (currentNumber % divideNumber != 0)
        {
            Toast.makeText(applicationContext,currentNumber.toString() + " is not divisible by " + divideNumber.toString(), Toast.LENGTH_LONG).show()
            return
        }
        moves--
        currentNumber /= divideNumber
        display(currentNumber)
    }
    fun ChangeSign(view: View)
    {
        moves--
        currentNumber *= -1
        display(currentNumber)
    }
    private fun display(number: Int) {
        moves = max(moves.toInt(), 0)
        numberTextView.text = "Current : " + number
        leftMovesTextView.text = "Left Moves : " + moves
        if (moves < 5)
        {
            leftMovesTextView.text = leftMovesTextView.text.toString() + " !"
            leftMovesTextView.setTextColor(Color.parseColor("#fd7e14"))
            numberTextView.setTextColor(Color.parseColor("#fd7e14"))
        }
        if (moves < 3)
        {
            leftMovesTextView.text = leftMovesTextView.text.toString() + "!"
            leftMovesTextView.setTextColor(Color.parseColor("#dc3545"))
            numberTextView.setTextColor(Color.parseColor("#dc3545"))
        }
        if (moves > 4)
        {
            leftMovesTextView.setTextColor(Color.parseColor("#20c997"))
            numberTextView.setTextColor(Color.parseColor("#20c997"))
        }
        if (currentNumber == finalNumber || moves == 0)
            EndGame(moves)
    }
    private fun EndGame(Number: Int)
    {
        val builder = AlertDialog.Builder(this@LevelsActivity)
        if (currentNumber == finalNumber)
        {
            builder.setMessage("Congratulations!! Won in " + ( 7 - moves) + " moves!!")
            builder.setNegativeButton("Try Again"){dialog, which ->
                Reset()
                Toast.makeText(applicationContext,"Try Again",Toast.LENGTH_LONG).show()
            }
            builder.setNeutralButton("OK"){_,_ ->
                divideButton.isEnabled = false
                multiplyButton.isEnabled = false
                minusButton.isEnabled = false
                plusButton.isEnabled = false
                changeSignButton.isEnabled = false
            }
            builder.setPositiveButton("Next Level"){dialog, which ->
                Toast.makeText(applicationContext, "El mfrod f hna next level", Toast.LENGTH_LONG).show()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        else if (moves == 0)
        {
            builder.setMessage("Loser !!")
            builder.setPositiveButton("Try Again"){dialog, which ->
                Reset()
                Toast.makeText(applicationContext,"Try Again",Toast.LENGTH_SHORT).show()
            }
            builder.setNeutralButton("OK"){_,_ ->
                divideButton.isEnabled = false
                multiplyButton.isEnabled = false
                minusButton.isEnabled = false
                plusButton.isEnabled = false
                changeSignButton.isEnabled = false
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}
