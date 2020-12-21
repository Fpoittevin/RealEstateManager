package com.ocr.francois.realestatemanager.ui.dataBindingUtils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ocr.francois.realestatemanager.utils.LocationTool
import com.ocr.francois.realestatemanager.utils.Utils
import org.joda.time.LocalDate

object PropertyBindingAdapter {
    @JvmStatic
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

    @JvmStatic
    @BindingAdapter("app:bindSoldDate")
    fun bindSoldDate(view: TextView, saleTimestamp: Long?) {
        saleTimestamp?.let {
            view.visibility = View.VISIBLE
            view.text = Utils.formatDate(LocalDate(saleTimestamp))
        } ?: run {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
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
}