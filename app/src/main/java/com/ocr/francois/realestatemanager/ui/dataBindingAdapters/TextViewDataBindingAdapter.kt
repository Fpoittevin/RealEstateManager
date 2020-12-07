package com.ocr.francois.realestatemanager.ui.dataBindingAdapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ocr.francois.realestatemanager.utils.Currency
import com.ocr.francois.realestatemanager.utils.Utils

@BindingAdapter(
    "bind:price", "bind:currency", "bind:default",
    requireAll = false
)
fun bindPriceWithDisplayedCurrency(
    view: TextView,
    price: Int,
    currency: Currency,
    default: Int?
) {
    var priceInCurrency = price

    default?.let{
        if(price == 0) {
            priceInCurrency = default
        }
    }

    if (currency == Currency.EURO) {
        priceInCurrency = Utils.convertDollarToEuro(priceInCurrency)
    }
    view.text = Utils.getFormattedPriceWithCurrency(currency, priceInCurrency)
}

@BindingAdapter(
    "bind:surface", "bind:default",
    requireAll = false
)
fun bindSurface(
    view: TextView,
    surface: Int?,
    default: Int?
) {
    surface?.let{
        var surfaceToDisplay = it

        default?.let { default ->
            if(surface == 0) {
                surfaceToDisplay = default
            }
        }

        val formattedSurface = Utils.formatNumber(surfaceToDisplay) + " m²"

        view.apply{
            visibility = View.VISIBLE
            text = formattedSurface
        }
    } ?: run {
        view.visibility = View.GONE
    }
}

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
        if(sliderValue == 0) {
            valueToDisplay = it
        }
    }

    view.text = Utils.formatNumber(valueToDisplay)
}