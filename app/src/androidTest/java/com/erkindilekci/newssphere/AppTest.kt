package com.erkindilekci.newssphere

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.erkindilekci.newssphere.presentation.listscreen.ListScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AppTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun itemAddedToScreen() {
        composeRule.activity.setContent {
            ListScreen(navController = rememberNavController())
        }

        composeRule.onNodeWithText("Title1").assertExists()
        composeRule.onNodeWithText("Title2").assertExists()
    }
}
