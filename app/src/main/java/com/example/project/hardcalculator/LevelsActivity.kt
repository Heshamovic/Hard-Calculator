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

    var lvlListIndex:Int = 0
    var ch:Boolean = false
    val allLevel: MutableList<level> = mutableListOf()
    var plusNumber:Long = 1
    var minusNumber:Long = 2
    var divideNumber:Long = 3
    var multplyNumber:Long = 4
    var initNumber:Long = 1
    var currentNumber:Long = initNumber
    var moves:Long = 7
    var finalNumber:Long = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.levels)
        while (allLevel.size < 50 && lvlListIndex < 5000)
        {
            lvlListIndex++
            initNumber = (Math.random() * 101).toLong()
            finalNumber = (Math.random() * 101).toLong()
            if (finalNumber == initNumber)
                continue
            plusNumber = (Math.random() * 101).toLong()
            minusNumber = (Math.random() * 101).toLong()
            multplyNumber = (Math.random() * 101).toLong()
            divideNumber = (Math.random() * 101).toLong()
            ch = false
            if (getLevels(0, initNumber))
                allLevel.add(level(initNumber, finalNumber, plusNumber, minusNumber, multplyNumber, divideNumber))
        }
        lvlListIndex = 0
        setNumbers()
        display()
    }
    fun getLevels(j:Long, value:Long):Boolean
    {
        if(value == finalNumber)
            ch = true
        if(j > 6 || ch || value >= 100000 || value <= -100000)
            return ch
        if (!ch)
            getLevels(j + 1, value * -1)
        if (!ch)
            getLevels(j + 1, value + plusNumber)
        if (!ch)
            getLevels(j + 1, value - minusNumber)
        if (!ch)
            getLevels(j + 1, value * multplyNumber)
        if (!ch && value % divideNumber == 0.toLong())
            getLevels(j + 1, value / divideNumber)
        return ch
    }
    fun Reset ()
    {
        currentNumber = initNumber
        moves = 7
        enableButtons()
        display()
    }
    fun ResetLevel(view: View)
    {
        Reset()
    }
    fun Plus(view: View)
    {
        if (currentNumber + plusNumber >= 100000)
        {
            Toast.makeText(applicationContext,"The number will be too large", Toast.LENGTH_LONG).show()
            return
        }
        moves--
        currentNumber += plusNumber
        display()
    }
    fun Minus(view: View)
    {
        if (currentNumber - minusNumber <= -100000)
        {
            Toast.makeText(applicationContext,"The number will be too small", Toast.LENGTH_LONG).show()
            return
        }
        moves--
        currentNumber -= minusNumber
        display()
    }
    fun Multiply(view: View)
    {
        if (currentNumber * multplyNumber >= 100000)
        {
            Toast.makeText(applicationContext,"The number will be too large", Toast.LENGTH_LONG).show()
            return
        }
        if (currentNumber * multplyNumber <= -100000)
        {
            Toast.makeText(applicationContext,"The number will be too small", Toast.LENGTH_LONG).show()
            return
        }
        moves--
        currentNumber *= multplyNumber
        display()
    }
    fun Divide(view: View)
    {
        if (currentNumber % divideNumber != 0.toLong())
        {
            Toast.makeText(applicationContext,currentNumber.toString() + " is not divisible by " + divideNumber.toString(), Toast.LENGTH_LONG).show()
            return
        }
        moves--
        currentNumber /= divideNumber
        display()
    }
    fun ChangeSign(view: View)
    {
        moves--
        currentNumber *= -1
        display()
    }
    private fun display() {
        moves = max(moves.toLong(), 0)
        numberTextView.text = "Current : " + currentNumber
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
        if (currentNumber == finalNumber || moves == 0.toLong())
            EndGame()
    }
    private fun EndGame()
    {
        disableButtons()
        val builder = AlertDialog.Builder(this@LevelsActivity)
        if (currentNumber == finalNumber)
        {
            builder.setMessage("Congratulations!! Won in " + ( 7 - moves) + " moves!!")
            builder.setNegativeButton("Try Again"){dialog, which ->
                Reset()
                Toast.makeText(applicationContext,"Try Again",Toast.LENGTH_LONG).show()
            }
            builder.setNeutralButton("OK"){_,_ ->

            }
            builder.setPositiveButton("Next Level"){dialog, which ->
                lvlListIndex++
                lvlListIndex %= allLevel.size
                setNumbers()
                display()
                Toast.makeText(applicationContext, "Level" + (lvlListIndex + 1).toString(), Toast.LENGTH_LONG).show()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        else if (moves == 0.toLong())
        {
            builder.setMessage("Loser !!")
            builder.setPositiveButton("Try Again"){dialog, which ->
                Reset()
                Toast.makeText(applicationContext,"Try Again",Toast.LENGTH_SHORT).show()
            }
            builder.setNeutralButton("OK"){_,_ ->
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
    fun disableButtons()
    {
        divideButton.isEnabled = false
        multiplyButton.isEnabled = false
        minusButton.isEnabled = false
        plusButton.isEnabled = false
        changeSignButton.isEnabled = false
    }
    fun enableButtons()
    {
        divideButton.isEnabled = true
        multiplyButton.isEnabled = true
        minusButton.isEnabled = true
        plusButton.isEnabled = true
        changeSignButton.isEnabled = true
    }
    fun setNumbers()
    {
        enableButtons()
        initNumber = allLevel[lvlListIndex].intiallNumber
        currentNumber = initNumber
        finalNumber = allLevel[lvlListIndex].finalNumber
        plusNumber = allLevel[lvlListIndex].pluslNumber
        multplyNumber = allLevel[lvlListIndex].multiplyNumber
        divideNumber = allLevel[lvlListIndex].divideNumber
        minusNumber = allLevel[lvlListIndex].minuslNumber
        moves = 7
        leftMovesTextView.text = "7"
        numberTextView.text = currentNumber.toString()
        plusButton.text = "+ " + plusNumber
        minusButton.text = "- " + minusNumber
        multiplyButton.text = "x " + multplyNumber
        divideButton.text = "/ " + divideNumber
        levelNumberTextView.text = (lvlListIndex + 1).toString()
        targetNumberTextView.text = "Target : " + finalNumber
    }
}
