package com.example.appiness.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers

class CustomAssertions {
    companion object {
        fun hasItemCount(count: Int): ViewAssertion {
            return RecyclerViewItemCountAssertion(count)
        }
    }

    private class RecyclerViewItemCountAssertion(private val count: Int) : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            if (view !is RecyclerView) {
                throw IllegalStateException("The asserted view is not recycler view")
            }

            if (view.adapter == null) {
                throw IllegalStateException("No Adapter is assigned to recycler view")
            }

            ViewMatchers.assertThat(
                "RecyclerView item count",
                view.adapter!!.itemCount, CoreMatchers.equalTo(count)
            )
        }

    }
}