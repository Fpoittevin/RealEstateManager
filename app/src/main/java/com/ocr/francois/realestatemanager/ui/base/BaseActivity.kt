package com.ocr.francois.realestatemanager.ui.base

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.events.FailureEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

open class BaseActivity : AppCompatActivity() {

    fun displayFragment(layoutId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(layoutId, fragment)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onFailureEvent(failureEvent: FailureEvent) {
        Log.e("ERROR", "onFailure: " + failureEvent.failureMessage);
        Toast.makeText(
            applicationContext,
            getString(R.string.unknown_error_message),
            Toast.LENGTH_LONG
        ).show()
    }
}