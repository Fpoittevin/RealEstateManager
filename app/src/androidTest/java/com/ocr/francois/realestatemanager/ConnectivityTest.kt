package com.ocr.francois.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ocr.francois.realestatemanager.testUtils.LiveDataTestUtil
import com.ocr.francois.realestatemanager.utils.Utils

import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConnectivityTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun connectivityTest() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val isConnectedFromUtilsLiveData = Utils.isInternetAvailable(appContext).apply {
            observeForever { }
        }

        Thread.sleep(3000)
        LiveDataTestUtil.getValue(isConnectedFromUtilsLiveData)?.let { assertTrue(it) }
    }
}