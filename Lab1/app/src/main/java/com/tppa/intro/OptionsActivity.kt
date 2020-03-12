package com.tppa.intro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_options.*
import java.io.File

class OptionsActivity : AppCompatActivity() {

    fun changeColor(view: View?) {
        val button: Button = view as Button
        when (button.text) {
            "White" -> {
                button.text = "Yellow"
                // File("color.txt").writeText("Yellow")
                optionsApp.setBackgroundColor(Color.parseColor("#FADA5E"))
            }
            "Yellow" -> {
                button.text = "Green"
                // File("color.txt").writeText("Green")
                optionsApp.setBackgroundColor(Color.parseColor("#1D800E"))
            }
            "Green" -> {
                button.text = "Red"
                // File("color.txt").writeText("Red")
                optionsApp.setBackgroundColor(Color.parseColor("#F51B00"))
            }
            else -> {
                button.text = "White"
                // File("color.txt").writeText("White")
                optionsApp.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)


        Log.i("Create Options", "onCreate()")
    }

    override fun onStart(){
        super.onStart()
        Log.i("Start Options", "onStart()")
    }

    override fun onPause(){
        super.onPause()
        Log.i("Pause Options", "onPause()")
    }

    override fun onResume(){
        super.onResume()
        Log.i("Resume Options", "onResume()")
    }

    override fun onStop(){
        super.onStop()
        Log.i("Stop Options", "onStop()")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("Destroy Options", "onDestroy()")
    }
}
