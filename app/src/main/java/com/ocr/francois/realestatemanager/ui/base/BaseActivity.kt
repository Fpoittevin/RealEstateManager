package com.ocr.francois.realestatemanager.ui.base

import android.app.Activity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.events.FailureEvent
import com.ocr.francois.realestatemanager.utils.IsInternetAvailableLiveData
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
        Log.e("ERROR", "onFailure: " + failureEvent.failureMessage)
        Toast.makeText(
            applicationContext,
            getString(R.string.unknown_error_message),
            Toast.LENGTH_LONG
        ).show()
    }

    fun observeConnexion() {
        IsInternetAvailableLiveData(this).observe(this, {
            if (!it) {
                MaterialAlertDialogBuilder(this).apply {
                    setPositiveButton(R.string.ok) { _, _ ->
                        finish()
                    }
                    setTitle(R.string.network_dialog_title)
                    setMessage(R.string.network_required)
                    create().also {
                        show()
                    }
                }
            }
        })
    }

    fun hideKeyboard() {
        val view = currentFocus ?: View(this)
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun configureToolbar(toolbar: MaterialToolbar, withHomeButton: Boolean) {
        setSupportActionBar(toolbar)

        if (withHomeButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}