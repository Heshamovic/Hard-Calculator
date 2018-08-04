package com.example.project.hardcalculator

import android.app.AlertDialog
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.R.string.cancel
import android.content.DialogInterface
import android.widget.Button


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
    var InitNumber = 1
    var moves = 7
    var FinalNumber = 25
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
        val currentMoves = findViewById<View>(R.id.LeftMoves_text_view) as TextView
        currentMoves.text = "Left Moves : " + moves
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
