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
    public void ensureTextChangesWork() {


//    onData(anything()).inAdapterView(withId(R.id.tea_grid_view)).atPosition(1).perform(click());

         //Checks that the OrderActivity opens with the correct tea name displayed
//    onView(withId(R.id.tea_name_text_view)).check(matches(withText(TEA_NAME)));

    }
}



