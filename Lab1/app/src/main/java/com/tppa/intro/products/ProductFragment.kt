package com.tppa.intro.products

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tppa.intro.R
import kotlinx.android.synthetic.main.product_layout.*

private const val ARG_PARAM1 = "product"
private const val ARG_PARAM2 = "description"

class ProductFragment : Fragment() {

    private var product: String? = null
    private var description: String? = null

    companion object {
        @JvmStatic
        fun newInstance(product: String, description: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, product)
                    putString(ARG_PARAM2, description)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fancierFont = Typeface.createFromAsset(activity!!.assets, "fonts/righteous.ttf")
        productName.typeface = fancierFont
        productDescription.typeface = fancierFont
        arguments?.let {
            product = it.getString(ARG_PARAM1)
            description = it.getString(ARG_PARAM2)
        }
        productName.text = product
        productDescription.text = description
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_layout, container, false)
    }
}
