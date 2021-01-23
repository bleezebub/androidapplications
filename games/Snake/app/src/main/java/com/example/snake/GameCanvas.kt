package com.example.snake

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.view.MotionEventCompat
import stanford.androidlib.graphics.GCanvas
import stanford.androidlib.graphics.GColor
import stanford.androidlib.graphics.GRect
import stanford.androidlib.graphics.GSprite
import java.lang.Math.abs
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.ArrayList


/*
* TODO List:
*  Take user input for directions
*  check if the head collided with its own body
*  check if the head collides with the wall and than end (Done)
*  Move the snake if its length is one (Done)
*  Move the head if the length is more than one
*  check if the head eats the apple and than increase its length
*  increment score
*  introduce the red-bigger apple which dissappears after a few seconds
*  add swipe listners(Done)
* use this link for gesture listening
*   */


class GameCanvas(context: Context?, attrs: AttributeSet) : GCanvas(context, attrs) {

    private var walls = ArrayList<GSprite>();
    private lateinit var head:GSprite
    private var snake = ArrayDeque<GSprite>()
    private var direction:Int = 0;
    private var apples = ArrayList<GSprite>()
    private var score = 0
    //constants
    companion object{
        private const val WALLTHICKNESS = 80f
        private const val NORTH = 0
        private const val WEST = 1
        private const val BODYTHICKNESS = 50f
        private const val SOUTH = 2
        private const val EAST = 3
        private const val APPLETHICKNESS = 30f
        private const val XSWIPE = 200f
        private const val YSWIPE = 200f
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun init() {
        this.backgroundColor = GColor.BLACK
        addWalls()
        addSnake()

        animate(120){
            tick()
        }
        var x1:Float = 0f
        var x2:Float = 0f
        var y1:Float = 0f
        var y2:Float = 0f
        setOnTouchListener{ _,event ->

            if(event.action == MotionEvent.ACTION_DOWN){
                x1 = event.x
                y1 = event.y
            }
            if(event.action == MotionEvent.ACTION_UP){
                x2 = event.x
                y2 = event.y
                if(x1>x2 && abs(x1-x2)> XSWIPE){
                    Toast.makeText(context, "leftswipe", Toast.LENGTH_SHORT).show()
                }else if(x1<x2 && abs(x1-x2)> XSWIPE) {
                    Toast.makeText(context, "rightswipe", Toast.LENGTH_SHORT).show()
                }else if(y1>y2 && abs(y1-y2)> YSWIPE){
                    Toast.makeText(context, "upswipe", Toast.LENGTH_SHORT).show()
                }else if(y2>y1 && abs(y1-y2)> YSWIPE){
                    Toast.makeText(context, "downswipe", Toast.LENGTH_SHORT).show()
                }

            }

            true

        }
    }
    private fun tick(){
        addApples()
        //checking if the head has collided with the wall
        for(wall in walls){
            if(wall.collidesWith(snake[0])){
                animationStop()
                val bob = AlertDialog.Builder(context)
                bob.setTitle("Game Over")
                bob.setMessage("Your Score: $score")
                bob.setPositiveButton("ok"){ _, _ ->
                }
                bob.create().show()
                (context as Activity).finish()
            }
        }




        if(snake.size == 1){
            if(direction == NORTH){
                snake[0].y--
            }else if(direction == WEST){
                snake[0].x--
            }else if(direction == SOUTH){
                snake[0].y++
            }else if(direction == EAST){
                snake[0].x++
            }
        }else{

        }
        updateAll()
    }
    private fun addApples(){
        if(apples.size == 3)
                return
        while(apples.size <= 3){
            val apple = GSprite(GRect(APPLETHICKNESS, APPLETHICKNESS).setFillColor(GColor.WHITE))
            val maxX = (this.width - WALLTHICKNESS).toInt()
            val minX = (WALLTHICKNESS).toInt()
            val maxY = (this.height - WALLTHICKNESS).toInt()
            val minY = (WALLTHICKNESS).toInt()

            apple.x = (Random().nextInt(maxX - minX + 1) + minX).toFloat()
            apple.y = (Random().nextInt(maxY - minY + 1) + minY).toFloat()
            apples.add((apple))
            add(apple)
        }
    }
    private fun addSnake() {
        head = GSprite(GRect(BODYTHICKNESS, BODYTHICKNESS).setFillColor(GColor.WHITE))
        head.x = this.width.toFloat()/2
        head.y = this.height.toFloat()/2
        snake.addLast(head)
        add(head)
    }

    private fun addWalls() {
        //right wall
        val rightwall = GSprite(GRect(WALLTHICKNESS, this.height.toFloat()).setFillColor(GColor.RED))
        rightwall.rightX = this.width.toFloat()
        rightwall.y = 0f;
        walls.add(rightwall)
        add(rightwall)

        //left wall
        val leftwall = GSprite(GRect(WALLTHICKNESS, this.height.toFloat()).setFillColor(GColor.RED))
        leftwall.x = 0f
        walls.add(leftwall)
        add(leftwall)

        //bottom wall
        val bottomWall = GSprite(GRect(this.width.toFloat(), WALLTHICKNESS).setFillColor(GColor.RED))
        bottomWall.x = 0f
        bottomWall.bottomY = this.height.toFloat()
        walls.add(bottomWall)
        add(bottomWall)

        val topWall = GSprite(GRect(this.width.toFloat(), WALLTHICKNESS).setFillColor(GColor.RED))
        topWall.x =0f
        topWall.y = 0f
        walls.add(topWall)
        add(topWall)
    }

}