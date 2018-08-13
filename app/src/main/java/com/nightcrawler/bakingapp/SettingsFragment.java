package com.nightcrawler.bakingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * shows the settings option for choosing the movie categories in ListPreference.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = SettingsFragment.class.getSimpleName();



    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        //add xml
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.getBoolean("show_bass",true);

        //to write in shared preferences
        //SharedPreferences.Editor editor=sharedPreferences.edit();
        //editor.putBoolean("show_bass",false);
//        editor.apply();


//        onSharedPreferenceChanged(sharedPreferences, getString(R.string.movies_categories_key));
    }

}





