package com.nightcrawler.bakingapp;

import android.support.test.espresso.intent.Checks;
import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

public class Test2 {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            =new ActivityTestRule<>(MainActivity.class);


    @Test
    public void AdapterViewTest() {

    onView(withId(R.id.dish1)).perform(click());
    onData(anything()).inAdapterView(withId(R.id.listView1)).atPosition(1).perform(click());
    onData(anything()).inAdapterView(withId(R.id.listView2)).atPosition(1).check(matches(withText("Graham Cracker crumbs-2 CUP")));

    }
}



