package com.tppa.intro.options

import android.app.Application
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.tppa.intro.R
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {

    private fun checkColor(sharedPreferences: SharedPreferences, key: String) {
        when (sharedPreferences.getString(key, "")) {
            "3" -> {
                optionsApp.setBackgroundColor(Color.parseColor("#1D800E"))
            }
            "2" -> {
                optionsApp.setBackgroundColor(Color.parseColor("#FADA5E"))
            }
            "1" -> {
                optionsApp.setBackgroundColor(Color.parseColor("#B90E0A"))
            }
            else -> {
                optionsApp.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            checkColor(sharedPreferences, key)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.optionsApp, OptionsFragment())
            .commit()

        val sharedPreferences: SharedPreferences = application.getSharedPreferences("com.tppa.sharedpreferences", Application.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        checkColor(sharedPreferences, "color")

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
