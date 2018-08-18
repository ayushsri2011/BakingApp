package com.nightcrawler.bakingapp;


import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule
                =new ActivityTestRule<>(MainActivity.class);


    @Test
    public void checkText() {

        String c="1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.";

        onView(withId(R.id.dish1)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listView1)).atPosition(3).perform(click());
        onView(withId(R.id.details)).check(matches(withText(c)));

    }

}
