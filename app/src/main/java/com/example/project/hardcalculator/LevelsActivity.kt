package com.example.project.hardcalculator

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.graphics.Color
import kotlinx.android.synthetic.main.levels.*
import kotlin.math.max
import android.content.SharedPreferences
import android.os.SystemClock
import java.io.*

class LevelsActivity : AppCompatActivity() {

    var lvl = 0
    var lvlListIndex:Int = 0
    val allLevel: MutableList<level> = mutableListOf()
    var plusNumber:Long = 1
    var minusNumber:Long = 2
    var divideNumber:Long = 3
    var multplyNumber:Long = 4
    var initNumber:Long = 1
    var currentNumber:Long = initNumber
    var moves:Long = 7
    var finalNumber:Long = 10
    val levelNumber = "levelNumber"
    var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.levels)
        var data = ""
        var ise = this.getResources().openRawResource(R.raw.levels)
        var reader = BufferedReader(InputStreamReader(ise))
        if (ise != null)
        {
            try {
                data = reader.readLine().toString()
                while(data != null)
                {
                    var x = 0
                    var i = 0
                    var a = IntArray(10)
                    for (c in data)
                    {
                        if (c == ' ')
                        {
                            a[i++] = x
                            x = 0
                        }
                        else
                        {
                            x *= 10
                            x += (c - '0')
                        }
                    }
                    a[i++] = x
                    x = 0
                    allLevel.add(level(a[0].toLong(), a[1].toLong(), a[2].toLong(), a[3].toLong(), a[4].toLong(), a[5].toLong()))
                    data = reader.readLine().toString()
                }
            }
            catch(e: Exception) {  }
        }
        prefs = this.getSharedPreferences("HardCalculatorPref", 0)
        lvl = prefs!!.getInt(levelNumber, 0)
        lvlListIndex = lvl
        setNumbers()
        customLevelTextView.minValue = 1
        customLevelTextView.maxValue = lvlListIndex + 1
        display()
    }
    fun Reset ()
    {
        timerClock.base = SystemClock.elapsedRealtime()
        timerClock.start()
        currentNumber = initNumber
        moves = 7
        enableButtons()
        display()
    }
    fun goLevel(view: View)
    {
        lvlListIndex = customLevelTextView.value - 1
        setNumbers()
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
            Toast.makeText(applicationContext,"The number will be too large", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(applicationContext,"The number will be too small", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(applicationContext,"The number will be too large", Toast.LENGTH_SHORT).show()
            return
        }
        if (currentNumber * multplyNumber <= -100000)
        {
            Toast.makeText(applicationContext,"The number will be too small", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(applicationContext,currentNumber.toString() + " is not divisible by " + divideNumber.toString(), Toast.LENGTH_SHORT).show()
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
        timerClock.stop()
        val elapsedMillis = SystemClock.elapsedRealtime() - timerClock.getBase()
        if (currentNumber == finalNumber)
        {
            builder.setMessage("Congratulations!! Won in " + ( 7 - moves) + " moves and " + (elapsedMillis / 1000) + " seconds !!")
            builder.setNegativeButton("Try Again"){dialog, which ->
                Reset()
                Toast.makeText(applicationContext,"Try Again",Toast.LENGTH_SHORT).show()
            }
            builder.setNeutralButton("OK"){_,_ ->
            }
            builder.setPositiveButton("Next Level"){dialog, which ->
                timerClock.base = SystemClock.elapsedRealtime()
                timerClock.start()
                setCurrentLevel(++lvlListIndex)
                lvlListIndex %= allLevel.size
                customLevelTextView.maxValue = max(lvlListIndex + 1, customLevelTextView.maxValue)
                setNumbers()
                display()
                Toast.makeText(applicationContext, "Level " + (lvlListIndex + 1).toString(), Toast.LENGTH_SHORT).show()
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
        timerClock.base = SystemClock.elapsedRealtime()
        timerClock.start()
        enableButtons()
        if (allLevel.isEmpty())
        {
            initNumber = 1
            currentNumber = initNumber
            finalNumber = 2
            plusNumber = 1
            multplyNumber = 1
            divideNumber = 1
            minusNumber = 1
        }
        else
        {
            initNumber = allLevel[lvlListIndex].intiallNumber
            currentNumber = initNumber
            finalNumber = allLevel[lvlListIndex].finalNumber
            plusNumber = allLevel[lvlListIndex].pluslNumber
            multplyNumber = allLevel[lvlListIndex].multiplyNumber
            divideNumber = allLevel[lvlListIndex].divideNumber
            minusNumber = allLevel[lvlListIndex].minuslNumber
        }
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
    private fun setCurrentLevel(level: Int) {
        if (lvl > level)
            return
        val editor = prefs!!.edit()
        editor.putInt(levelNumber, level)
        editor.apply()
    }
}
