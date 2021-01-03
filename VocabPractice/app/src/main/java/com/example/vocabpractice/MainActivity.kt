package com.example.vocabpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private var hashMap: HashMap<String, String> = HashMap<String, String>()
    private var definitions: ArrayList<String> = ArrayList<String>()
    private var words: ArrayList<String> = ArrayList<String>()
    private var options:ArrayList<String> = ArrayList<String>()
    private var score = 0;
    private lateinit var currentWord:String
    private  lateinit var correctDefns:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readFile()
        displayAgain()

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)
        val wordMeanings = findViewById<ListView>(R.id.word_meanings)
        wordMeanings.adapter = arrayAdapter;

        wordMeanings.setOnItemClickListener{parent, view, index, id->
//            options.removeAt(index)
            val chosen = options[index]
            if(chosen == correctDefns)
                    score++;
            else
                score--
            findViewById<TextView>(R.id.score).text = "Score = $score"
            displayAgain()
            arrayAdapter.notifyDataSetChanged()
        }

    }

    private fun displayAgain() {
        val rand = Random()
        val index = rand.nextInt(words.size)
        currentWord = words[index].toString()
        val wordDisplay = findViewById<TextView>(R.id.word)
        wordDisplay.text = currentWord

        correctDefns = hashMap[currentWord].toString()
        definitions.shuffle()
        var i=0;
        options.clear()
        while(options.size!=3){
            if(definitions[i] == correctDefns)
                continue
            options.add(definitions[i])
            i++
        }
        options.add(correctDefns!!)
        options.shuffle()
    }

    private fun readFile(){
        val scanner = Scanner(resources.openRawResource(R.raw.barrons))
        while(scanner.hasNext()){
            val cur = scanner.nextLine().split(":")
//            Log.d("values", "$cur")
            hashMap.put(cur[0], cur[1])
            definitions.add(cur[1])
            words.add(cur[0])
        }
    }
}