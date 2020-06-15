package com.ocr.francois.realestatemanager.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {

    fun displayFragment(layoutId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(layoutId, fragment)
            .commit()
    }
}