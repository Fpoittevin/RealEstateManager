package com.ocr.francois.realestatemanager

import androidx.core.content.FileProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ocr.francois.realestatemanager.ui.MainActivity
import com.ocr.francois.realestatemanager.utils.ImageUtil
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var file: File

    @Before
    fun createActivity() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }
    /*
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ocr.francois.realestatemanager", appContext.packageName)
    }
*/

    @Test
    fun deleteFileTest() {

        scenario.onActivity { activity ->
            file = ImageUtil.createImageFile(activity)
            assertTrue(file.exists())

            val uri = FileProvider.getUriForFile(
                activity,
                InstrumentationRegistry.getInstrumentation().targetContext.packageName,
                file
            )
            if (file.exists()) {
                ImageUtil.deleteFileFromUri(uri, activity)
            }
            assertFalse(file.exists())
        }
    }
}