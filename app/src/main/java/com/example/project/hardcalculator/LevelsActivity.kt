package com.example.project.hardcalculator

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.graphics.Color
import android.widget.Button
import kotlin.math.max


class LevelsActivity : AppCompatActivity() {

    var plusNumber = 1
    var minusNumber = 2
    var divideNumber = 3
    var multplyNumber = 4
    var InitNumber = 1
    var currentNumber = InitNumber
    var moves = 7
    var FinalNumber = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.levels)
        val plusBtn = findViewById<View>(R.id.Plus_button_view) as TextView
        val minusBtn = findViewById<View>(R.id.Minus_button_view) as TextView
        val multplyBtn = findViewById<View>(R.id.Multply_button_view) as TextView
        val divideBtn = findViewById<View>(R.id.Divide_button_view) as TextView
        plusBtn.text = "+ " + plusNumber.toString()
        minusBtn.text = "- " + minusNumber.toString()
        multplyBtn.text = "x " + multplyNumber.toString()
        divideBtn.text = "/ " + divideNumber.toString()
        display(InitNumber)
    }
    fun Reset ()
    {
        currentNumber = InitNumber
        moves = 7
        val divBtn = findViewById<View>(R.id.Divide_button_view) as Button
        val mulBtn = findViewById<View>(R.id.Multply_button_view) as Button
        val minusBtn = findViewById<View>(R.id.Minus_button_view) as Button
        val plusBtn = findViewById<View>(R.id.Plus_button_view) as Button
        val signBtn = findViewById<View>(R.id.changeSign_button_view) as Button
        divBtn.isEnabled = true
        mulBtn.isEnabled = true
        minusBtn.isEnabled = true
        plusBtn.isEnabled = true
        signBtn.isEnabled = true
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
    fun Multply(view: View)
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
        val currentNumberTextView = findViewById<View>(R.id.Number_text_view) as TextView
        currentNumberTextView.text = "" + number
        val currentMoves = findViewById<View>(R.id.LeftMoves_text_view) as TextView
        currentMoves.text = "Left Moves : " + moves
        if (moves < 5)
        {
            currentMoves.text = currentMoves.text.toString() + " !"
            currentMoves.setTextColor(Color.parseColor("#fd7e14"))
        }
        if (moves < 3)
        {
            currentMoves.text = currentMoves.text.toString() + "!"
            currentMoves.setTextColor(Color.parseColor("#dc3545"))
        }
        if (moves > 4)
            currentMoves.setTextColor(Color.parseColor("#20c997"))
        if (currentNumber == FinalNumber || moves == 0)
            EndGame(moves);
    }
    private fun EndGame(Number: Int)
    {
        val builder = AlertDialog.Builder(this@LevelsActivity)
        if (currentNumber == FinalNumber)
        {
            builder.setMessage("Congratulations!! Won in " + ( 7 - moves) + " moves!!")
            builder.setNegativeButton("Try Again"){dialog, which ->
                Reset()
                Toast.makeText(applicationContext,"Try Again",Toast.LENGTH_LONG).show()
            }
            builder.setNeutralButton("OK"){_,_ ->
                val divBtn = findViewById<View>(R.id.Divide_button_view) as Button
                val mulBtn = findViewById<View>(R.id.Multply_button_view) as Button
                val minusBtn = findViewById<View>(R.id.Minus_button_view) as Button
                val plusBtn = findViewById<View>(R.id.Plus_button_view) as Button
                val signBtn = findViewById<View>(R.id.changeSign_button_view) as Button
                divBtn.isEnabled = false
                mulBtn.isEnabled = false
                minusBtn.isEnabled = false
                plusBtn.isEnabled = false
                signBtn.isEnabled = false
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
                val divBtn = findViewById<View>(R.id.Divide_button_view) as Button
                val mulBtn = findViewById<View>(R.id.Multply_button_view) as Button
                val minusBtn = findViewById<View>(R.id.Minus_button_view) as Button
                val plusBtn = findViewById<View>(R.id.Plus_button_view) as Button
                val signBtn = findViewById<View>(R.id.changeSign_button_view) as Button
                divBtn.isEnabled = false
                mulBtn.isEnabled = false
                minusBtn.isEnabled = false
                plusBtn.isEnabled = false
                signBtn.isEnabled = false
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}
