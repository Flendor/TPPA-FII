package com.tppa.intro

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import com.tppa.intro.products.ProductFragment


class MainActivity : AppCompatActivity() {

    var isProductFragmentOn = false

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.options -> {
                val intent = Intent(this, OptionsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.help -> {
                Log.i("HELP", "HELP WAS PRESSED")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val products = listOf("Beans", "Yogurt", "Milk", "Apples", "Chicken meat", "Ice cream")
        val productsWithDescriptions = mutableMapOf<String, String>()
        productsWithDescriptions[products[0]] = "One of the vegetarian foods that are rich in proteins. They are healthy."
        productsWithDescriptions[products[1]] = "Sour, cold and healthy goodness."
        productsWithDescriptions[products[2]] = "Drink this before sleep and you will feel cozy."
        productsWithDescriptions[products[3]] = "One apple a day keeps the doctor away."
        productsWithDescriptions[products[4]] = "Eat this after a workout and you will double your muscle mass in just 2 hours."
        productsWithDescriptions[products[5]] = "It's good but it's not that healthy."

        val fancierFont = Typeface.createFromAsset(assets, "fonts/righteous.ttf")
        description.typeface = fancierFont

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
                productsWithDescriptions[parent.getItemAtPosition(position).toString()]!!)
            // description.text = productsWithDescriptions[parent.getItemAtPosition(position).toString()]
        }

        Log.i("Create", "onCreate()")
    }

    private fun goToFragment(product: String, description: String) {
        val fmManager = supportFragmentManager
        val transaction = fmManager.beginTransaction()
        val fragment: Fragment
        fragment = ProductFragment.newInstance(product, description)
        fmManager.popBackStackImmediate()
        transaction.replace(R.id.mainApp, fragment).addToBackStack(null).commit()
        isProductFragmentOn = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Description", description.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        description.text = savedInstanceState.getString("Description")
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
