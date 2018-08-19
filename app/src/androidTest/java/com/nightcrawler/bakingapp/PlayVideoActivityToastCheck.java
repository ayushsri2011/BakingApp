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
public class PlayVideoActivityToastCheck {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            =new ActivityTestRule<>(MainActivity.class);

    //testing to check ingredients displayed for the recipes in the PlayVideoActivity
    //ensure net is available

    @Test
    public void checkIngredientsExist1() {
        onView(withText("Nutella Pie")).perform(click());
        onView(withText("Recipe Ingredients")).perform(click());
        onView(withText("Recipe Ingredients")).perform(click());
        onView(withText("Graham Cracker crumbs-2 CUP")).check(matches(isDisplayed()));
        onView(withText("unsalted butter, melted-6 TBLSP")).check(matches(isDisplayed()));

    }

    @Test
    public void checkIngredientsExist2() {
        onView(withText("Yellow Cake")).perform(click());
        onView(withText("Recipe Ingredients")).perform(click());
        onView(withText("Recipe Ingredients")).perform(click());
        onView(withText("sifted cake flour-400 G")).check(matches(isDisplayed()));
        onView(withText("granulated sugar-700 G")).check(matches(isDisplayed()));

    }

    @Test
    public void checkIngredientsExist3() {
        onView(withText("Cheesecake")).perform(click());
        onView(withText("Recipe Ingredients")).perform(click());
        onView(withText("Recipe Ingredients")).perform(click());
        onView(withText("Graham Cracker crumbs-2 CUP")).check(matches(isDisplayed()));
        onView(withText("unsalted butter, melted-6 TBLSP")).check(matches(isDisplayed()));

    }

    @Test
    public void checkIngredientsExist4() {
        onView(withText("Brownies")).perform(click());
        onView(withText("Recipe Ingredients")).perform(click());
        onView(withText("Recipe Ingredients")).perform(click());
        onView(withText("Bittersweet chocolate (60-70% cacao)-350 G")).check(matches(isDisplayed()));
        onView(withText("unsalted butter-226 G")).check(matches(isDisplayed()));

    }

}
