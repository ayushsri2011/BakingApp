package com.nightcrawler.bakingapp;

import android.os.IBinder;
import android.support.test.espresso.Root;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {

    //creating a custom matcher to test Toasts

    @Override
    public void describeTo(Description description) {
        description.appendText("is Toast");
    }

    @Override
    protected boolean matchesSafely(Root root) {
        int type  = root.getWindowLayoutParams().get().type;
        if(type == WindowManager.LayoutParams.TYPE_TOAST)
        {
            IBinder windowToken = root.getDecorView().getWindowToken();
            IBinder appToken = root.getDecorView().getApplicationWindowToken();
            if(windowToken==appToken)
                return true;
        }
        return false;
    }


}
