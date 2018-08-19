package com.nightcrawler.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.os.Bundle;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class Main2ActivityIntentTest {

    //Testing Launch of Main2Activity from MainActivity
    //Test can be run on both Mobile and Tabs(600dp or more)
    @Rule
    public ActivityTestRule<Main2Activity> rule =
            new ActivityTestRule(Main2Activity.class, true, false);

    @Test
    public void SecondActivityLaunched1() {

        //Launching Main2Activity for NutellaPie
        Intent intent = new Intent();
        Bundle args = new Bundle();
            args.putInt("KEY", 1);
        intent.putExtra("BUNDLE", args);
        rule.launchActivity(intent);
        onView(withText("Recipe Ingredients")).check(matches(isDisplayed()));
    }


    @Test
    public void SecondActivityLaunched2() {

        //Launching Main2Activity for Brownies
        Intent intent = new Intent();
        Bundle args = new Bundle();
        args.putInt("KEY", 3);
        intent.putExtra("BUNDLE", args);
        rule.launchActivity(intent);
        onView(withText("Recipe Ingredients")).check(matches(isDisplayed()));
    }

    @Test
    public void SecondActivityLaunched3() {

        //Launching Main2Activity for YellowCake
        Intent intent = new Intent();
        Bundle args = new Bundle();
        args.putInt("KEY", 2);
        intent.putExtra("BUNDLE", args);
        rule.launchActivity(intent);
        onView(withText("Recipe Ingredients")).check(matches(isDisplayed()));
    }

    @Test
    public void SecondActivityLaunched4() {

        //Launching Main2Activity for Cheesecake
        Intent intent = new Intent();
        Bundle args = new Bundle();
        args.putInt("KEY", 4);
        intent.putExtra("BUNDLE", args);
        rule.launchActivity(intent);
        onView(withText("Recipe Ingredients")).check(matches(isDisplayed()));
    }
}










