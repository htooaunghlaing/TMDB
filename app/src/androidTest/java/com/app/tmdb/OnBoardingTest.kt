package com.app.tmdb

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OnBoardingTest {

    private val device: UiDevice

    init {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        device = UiDevice.getInstance(instrumentation)
    }

    /**
     * Run before the method with @Test annotation
     */
    @Before
    fun before() {
        device.pressHome()
    }


    /**
     * Run after each method with @Test annotation
     */
    @After
    fun after() {
        device.pressHome()
    }

    @Test
    fun validateWifi() {
        // Open apps list by scrolling on home screen
        val workspace = device.findObject(
            By.res("com.google.android.apps.pixellauncher:id/workspace")
        )
        workspace.scroll(Direction.DOWN, 2.0f)

        // Click on Settings icon to launch the app
        val tmdb = device.findObject(
            By.res("com.google.android.apps.pixellauncher:id/icon").text("TMDB")
        )
        tmdb.click()


        //click
        val skip = device.wait(Until.findObject(By.text("SKIP")), 2000)
        skip.click()

        Thread.sleep(1500)

        skip.click()

        Thread.sleep(1500)

        val gotIt = device.wait(Until.findObject(By.text("GOT IT").res("com.app.tmdb:id/btn_next")), 2000)

        Assert.assertEquals("GOT IT", gotIt.text)
    }
}