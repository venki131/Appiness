package com.example.appiness.presentation.view


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.appiness.R
import com.example.appiness.presentation.view.CustomAssertions.Companion.hasItemCount
import com.example.appiness.presentation.view.CustomMatchers.Companion.withItemCount
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
    }

    @Test
    fun countPrograms() {
        onView(withId(R.id.recyclerView))
            .check(matches(withItemCount(101)))
    }

    @Test
    fun countProgramsWithViewAssertion() {
        onView(withId(R.id.recyclerView))
            .check(hasItemCount(101))
    }
}
