package com.tppa.intro

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import com.tppa.intro.location.LocationActivity
import com.tppa.intro.options.OptionsActivity
import com.tppa.intro.sensors.SensorsActivity
import com.tppa.intro.products.ProductFragment


class MainActivity : AppCompatActivity() {

    var isProductFragmentOn = false
    private val products = listOf("Beans", "Yogurt", "Milk", "Apples", "Chicken meat", "Ice cream")
    private val productsWithDescriptions = mutableMapOf<String, String>()
    private val productsWithPrices = mutableMapOf<String, String>()

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0 || !isProductFragmentOn)
            super.onBackPressed()
        else {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Go back to shop?")
                .setPositiveButton("Yes!") { _, _ ->
                    isProductFragmentOn = false
                    super.onBackPressed()
                }
                .setNegativeButton("Not yet!", null)
                .show()
        }
    }

    fun writeToDB() {
        val myDatabase: SQLiteDatabase = this@MainActivity.openOrCreateDatabase("Grocery Store", Context.MODE_PRIVATE, null)
        myDatabase.execSQL("DROP TABLE IF EXISTS FOOD")
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS FOOD (name TEXT, description TEXT, price TEXT)")
        for (index in products.indices)
            myDatabase.execSQL("INSERT INTO FOOD (name, description, price) VALUES (?, ?, ?)", arrayOf(products.get(index),
                productsWithDescriptions[products.get(index)], productsWithPrices[products.get(index)]))
        Toast.makeText(this, "Successfully saved products in a database locally!", Toast.LENGTH_SHORT).show()

        // just checking if data was inserted correctly
        val c: Cursor = myDatabase.rawQuery("SELECT * FROM FOOD", null)
        val nameIndex: Int = c.getColumnIndex("name")
        val descriptionIndex: Int = c.getColumnIndex("description")
        val priceIndex: Int = c.getColumnIndex("price")
        while (c.moveToNext()) {
            Log.i("NAME", c.getString(nameIndex))
            Log.i("DESCRIPTION", c.getString(descriptionIndex))
            Log.i("PRICE", c.getString(priceIndex))
        }
        c.close()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.options -> {
                val intent = Intent(this, OptionsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.saveData -> {
                writeToDB()
                true
            }
            R.id.sensorsListen -> {
                val intent = Intent(this, SensorsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.location -> {
                val intent = Intent(this, LocationActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.help -> {
                Toast.makeText(this, "Just buy stuff!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun checkColor(sharedPreferences: SharedPreferences, key: String) {
        when (sharedPreferences.getString(key, "")) {
            "3" -> {
                mainApp.setBackgroundColor(Color.parseColor("#1D800E"))
            }
            "2" -> {
                mainApp.setBackgroundColor(Color.parseColor("#FADA5E"))
            }
            "1" -> {
                mainApp.setBackgroundColor(Color.parseColor("#B90E0A"))
            }
            else -> {
                mainApp.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            checkColor(sharedPreferences, key)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productsWithDescriptions[products[0]] = "One of the vegetarian foods that are rich in proteins. They are healthy."
        productsWithDescriptions[products[1]] = "Sour, cold and healthy goodness."
        productsWithDescriptions[products[2]] = "Drink this before sleep and you will feel cozy."
        productsWithDescriptions[products[3]] = "One apple a day keeps the doctor away."
        productsWithDescriptions[products[4]] = "Eat this after a workout and you will double your muscle mass in just 2 hours!!!"
        productsWithDescriptions[products[5]] = "It's good but it's not that healthy."

        productsWithPrices[products[0]] = "4 RON / KG"
        productsWithPrices[products[1]] = "3 RON / 330 ML"
        productsWithPrices[products[2]] = "5 RON / 1L"
        productsWithPrices[products[3]] = "4 RON / KG"
        productsWithPrices[products[4]] = "10 RON / KG"
        productsWithPrices[products[5]] = "5 RON / 100 MG"

        val fancierFont = Typeface.createFromAsset(assets, "fonts/righteous.ttf")

        val productAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, products) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val tv = view.findViewById<View>(android.R.id.text1) as TextView
                tv.setTextColor(Color.BLACK)
                tv.typeface = fancierFont
                tv.gravity = 17
                tv.textSize = 24f
                return view
            }
        }

        myListView.adapter = productAdapter

        myListView.setOnItemClickListener{ parent, _, position, _ ->
            goToFragment(parent.getItemAtPosition(position).toString(),
                productsWithDescriptions[parent.getItemAtPosition(position).toString()]!!,
                productsWithPrices[parent.getItemAtPosition(position).toString()]!!)
        }

        val sharedPreferences: SharedPreferences = application.getSharedPreferences("com.tppa.sharedpreferences", Application.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        checkColor(sharedPreferences, "color")

        Log.i("Create", "onCreate()")
    }

    private fun goToFragment(product: String, description: String, price: String) {
        val fmManager = supportFragmentManager
        val transaction = fmManager.beginTransaction()
        val fragment: Fragment
        fragment = ProductFragment.newInstance(product, description, price)
        fmManager.popBackStackImmediate()
        transaction.replace(R.id.mainApp, fragment).addToBackStack(null).commit()
        isProductFragmentOn = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("IsFragmentOn", isProductFragmentOn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isProductFragmentOn = savedInstanceState.getBoolean("IsFragmentOn")
    }

    override fun onStart(){
        super.onStart()
        Log.i("Start", "onStart()")
    }

    override fun onPause(){
        super.onPause()
        Log.i("Pause", "onPause()")
    }

    override fun onResume(){
        super.onResume()
        Log.i("Resume", "onResume()")
    }

    override fun onStop(){
        super.onStop()
        Log.i("Stop", "onStop()")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("Destroy", "onDestroy()")
    }
}
