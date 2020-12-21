package com.ocr.francois.realestatemanager.ui.dataBindingUtils

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.Slider

object SliderDataBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:value")
    fun setValue(view: Slider, newValue: Int?) {
        if (view.value.toInt() != newValue) {
            newValue?.let {
                view.value = newValue.toFloat()
            } ?: run {
                view.value = view.valueFrom
            }
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:value")
    fun getValue(view: Slider): Int? {
        return if (view.value != view.valueFrom) {
            view.value.toInt()
        } else {
            null
        }
    }

    @JvmStatic
    @BindingAdapter("android:valueAttrChanged")
    fun setSliderListeners(slider: Slider, attrChange: InverseBindingListener) {
        slider.addOnChangeListener { _, _, _ ->
            attrChange.onChange()
        }
    }
}