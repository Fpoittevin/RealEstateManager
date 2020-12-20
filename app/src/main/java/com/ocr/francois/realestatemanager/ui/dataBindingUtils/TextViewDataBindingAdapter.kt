package com.ocr.francois.realestatemanager.ui.dataBindingUtils

import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputEditText
import com.ocr.francois.realestatemanager.utils.Currency
import com.ocr.francois.realestatemanager.utils.Utils

object TextViewDataBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        "app:price", "app:currency", "app:default",
        requireAll = false
    )
    fun bindPriceWithDisplayedCurrency(
        view: TextView,
        price: Int?,
        currency: Currency?,
        default: Int?
    ) {
        price?.let {
            var priceInCurrency = it

            default?.let { default ->
                if (price == 0) {
                    priceInCurrency = default
                }
            }

            currency?.let { currency ->
                if (currency == Currency.EURO) {
                    priceInCurrency = Utils.convertDollarToEuro(priceInCurrency)
                }
                view.text = Utils.getFormattedPriceWithCurrency(currency, priceInCurrency)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(
        "bind:surface", "bind:default",
        requireAll = false
    )
    fun bindSurface(
        view: TextView,
        surface: Int?,
        default: Int?
    ) {
        surface?.let {
            var surfaceToDisplay = it

            default?.let { default ->
                if (surface == 0) {
                    surfaceToDisplay = default
                }
            }

            val formattedSurface = Utils.formatNumber(surfaceToDisplay) + " m²"

            view.apply {
                visibility = View.VISIBLE
                text = formattedSurface
            }
        } ?: run {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter(
        "bind:sliderValue", "bind:default",
        requireAll = false
    )
    fun bindSliderValue(
        view: TextView,
        sliderValue: Int,
        default: Int?
    ) {
        var valueToDisplay = sliderValue

        default?.let {
            if (sliderValue == 0) {
                valueToDisplay = it
            }
        }
        view.text = Utils.formatNumber(valueToDisplay)
    }

    @JvmStatic
    @BindingAdapter("bind:priceText", "bind:currency")
    fun setValue(view: TextInputEditText, priceText: Int, currency: Currency) {
        if (view.text.toString() != priceText.toString()) {
            if (currency == Currency.EURO) {
                view.setText(Utils.convertDollarToEuro(priceText).toString())
            } else {
                view.setText(priceText.toString())
            }
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "bind:priceText")
    fun getValue(view: TextInputEditText): Int? {
        return if (!view.text.isNullOrEmpty()) {
            view.text.toString().toInt()
        } else null
    }

    @JvmStatic
    @BindingAdapter("app:priceTextAttrChanged")
    fun setTextInputListener(view: TextInputEditText, attrChange: InverseBindingListener) {
        view.addTextChangedListener {
            attrChange.onChange()
        }
    }
}