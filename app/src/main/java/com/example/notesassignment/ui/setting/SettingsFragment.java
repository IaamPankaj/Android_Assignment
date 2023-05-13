package com.example.notesassignment.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.notesassignment.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        ListPreference themePreference = findPreference("theme_preference");
        themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            String selectedTheme = (String) newValue;
            // Apply the selected theme to the app
            applyTheme(selectedTheme);
            return true;
        });
    }

    private void applyTheme(String theme) {
        switch (theme) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    private void restartActivity() {
        Intent intent = requireActivity().getIntent();
        requireActivity().finish();
        startActivity(intent);
    }


}


