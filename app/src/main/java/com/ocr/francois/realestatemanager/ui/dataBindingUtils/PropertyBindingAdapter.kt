package com.ocr.francois.realestatemanager.ui.dataBindingUtils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.ocr.francois.realestatemanager.utils.Currency
import com.ocr.francois.realestatemanager.utils.LocationTool
import com.ocr.francois.realestatemanager.utils.Utils
import org.joda.time.LocalDate

@BindingAdapter("app:bindText")
fun bindText(view: TextView, text: String?) {

    if (text.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        view.text = text
    }
}


@BindingAdapter(
    "bind:address_first", "bind:address_second", "bind:city", "bind:zip_code",
    requireAll = false
)
fun bindAddress(
    view: TextView,
    addressFirst: String?,
    addressSecond: String?,
    city: String?,
    zipCode: String?
) {
    view.text = LocationTool.addressConcatenation(
        addressFirst,
        addressSecond,
        city,
        zipCode,
        true
    )
}

@BindingAdapter("app:bindSoldDate")
fun bindSoldDate(view: TextView, saleTimestamp: Long?) {
    saleTimestamp?.let {
        view.visibility = View.VISIBLE
        view.text = Utils.formatDate(LocalDate(saleTimestamp))
    } ?: run {
        view.visibility = View.GONE
    }
}

@BindingAdapter("app:bindPhotoUri")
fun bindPhotoUri(view: ImageView, uri: String?) {
    uri?.let {
        Glide
            .with(view.context)
            .load(uri)
            .centerCrop()
            .into(view)
    }
}