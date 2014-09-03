package com.liquications.polyphasicsleep;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.settings);
        addPreferencesFromResource(R.xml.settings);
    }

}
