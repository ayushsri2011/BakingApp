package com.nightcrawler.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class Test3 {

    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<DetailsActivity> rule =
            new ActivityTestRule(DetailsActivity.class, true, false);

    @Test
    public void demonstrateIntentPrep() {
        Bundle args = new Bundle();
        args.putInt("KEY", 1);

        Intent intent = new Intent();
        intent.putExtra("BUNDLE", args);
        rule.launchActivity(intent);
        onView(withId(R.id.dish1)).check(matches(withText("Test")));
    }
}