package com.tppa.intro.products

import android.app.Application
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import com.tppa.intro.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_layout.*

private const val ARG_PARAM1 = "product"
private const val ARG_PARAM2 = "description"
private const val ARG_PARAM3 = "price"

class ProductFragment : Fragment() {

    private var product: String? = null
    private var description: String? = null
    private var price: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        @JvmStatic
        fun newInstance(product: String, description: String, price: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, product)
                    putString(ARG_PARAM2, description)
                    putString(ARG_PARAM3, price)
                }
            }
    }

    private fun checkColor(sharedPreferences: SharedPreferences, key: String) {
        when (sharedPreferences.getString(key, "")) {
            "3" -> {
                mainFragment.setBackgroundColor(Color.parseColor("#1D800E"))
            }
            "2" -> {
                mainFragment.setBackgroundColor(Color.parseColor("#FADA5E"))
            }
            "1" -> {
                mainFragment.setBackgroundColor(Color.parseColor("#B90E0A"))
            }
            else -> {
                mainFragment.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fancierFont = Typeface.createFromAsset(activity!!.assets, "fonts/righteous.ttf")
        productName.typeface = fancierFont
        productDescription.typeface = fancierFont
        productPrice.typeface = fancierFont
        arguments?.let {
            product = it.getString(ARG_PARAM1)
            description = it.getString(ARG_PARAM2)
            price = it.getString(ARG_PARAM3)
        }

        productName.text = product
        productDescription.text = description
        productPrice.text = price
        when(product) {
            "Beans" -> {
                productImage.setImageResource(R.drawable.beans)
            }
            "Yogurt" -> {
                productImage.setImageResource(R.drawable.yogurt)
            }
            "Milk" -> {
                productImage.setImageResource(R.drawable.milk)
            }
            "Apples" -> {
                productImage.setImageResource(R.drawable.apple)
            }
            "Chicken meat" -> {
                productImage.setImageResource(R.drawable.chicken)
            }
            "Ice cream" -> {
                productImage.setImageResource(R.drawable.cream)
            }
        }
        sharedPreferences = activity!!.application.getSharedPreferences("com.tppa.sharedpreferences", Application.MODE_PRIVATE)
        checkColor(sharedPreferences, "color")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_layout, container, false)
    }

    override fun onResume() {
        checkColor(sharedPreferences, "color")
        super.onResume()
    }
}
