package com.example.firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateButtons()
    }

    private fun updateButtons(){
        var new_left = (0 .. 1000).random()
        var new_right = (0 .. 1000).random()
        Toast.makeText(this, "$new_left and $new_right", Toast.LENGTH_SHORT).show()
        val lef= findViewById<Button>(R.id.button)
        val rgt= findViewById<Button>(R.id.button2)
        lef.text = new_left.toString()
        rgt.text = new_right.toString()
    }


    private var score: Int = 0
    private fun updateScore(){
        val score_display = findViewById<TextView>(R.id.scoredisplay)
        score_display.text = score.toString()
        updateButtons()
    }
    fun leftButtonClick(view: View) {
        val lef: Int = findViewById<Button>(R.id.button).text.toString().toInt()
        val rgt: Int = findViewById<Button>(R.id.button2).text.toString().toInt()
        if(lef>=rgt)
            score++
        else
            score--

        updateScore()
    }
    fun onRightButtonClick(view: View) {
        val lef: Int = findViewById<Button>(R.id.button).text.toString().toInt()
        val rgt: Int = findViewById<Button>(R.id.button2).text.toString().toInt()
        if(lef<=rgt)
                score++
        else
                score--

        updateScore()
    }


}