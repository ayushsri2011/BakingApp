package com.nightcrawler.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DataConnectivityToastMainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            =new ActivityTestRule<>(MainActivity.class);


    @Test
    public void checkStartupToast() {
        //run with airplane mode so no internet connectivity
        onView(withText("Ensure data connectivity")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkButtonClickToast1() {
        //run with airplane mode so no internet connectivity
        onView(withText("Nutella Pie")).perform(click());
        onView(withText("Ensure data connectivity")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkButtonClickToast2() {
        //run with airplane mode so no internet connectivity
        onView(withText("Brownies")).perform(click());
        onView(withText("Ensure data connectivity")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkButtonClickToast3() {
        //run with airplane mode so no internet connectivity
        onView(withText("Yellow Cake")).perform(click());
        onView(withText("Ensure data connectivity")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkButtonClickToast4() {
        //run with airplane mode so no internet connectivity
        onView(withText("Cheesecake")).perform(click());
        onView(withText("Ensure data connectivity")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

}
