package com.uwechue.nycdemo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.uwechue.nycdemo.utility.Utils;
import com.uwechue.nycdemo.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBasicNetworkError() {
        if (!Utils.isNetworkAvailable(mActivityRule.getActivity().getApplicationContext())) {
            onView(withText(R.string.callback_error)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void testClickOnFAB() {
        onView(withId(R.id.fab)).perform(click());
    }


}