package com.liquications.polyphasicsleep;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.liquications.polyphasicsleep.database.SleepNowDatabase;


/**
 * Created by Mike Clarke 19/08/2014.
 *
 */
public class AlarmFrag extends Fragment {

    private Spinner spinner;
    ImageButton setCustom;
    ImageButton sleepNow;
    Intent alarmIntent;
    Intent alarmIntent2;
    int scheduleSelected = 1;
    ImageButton setDefault;
    int perfsDefaultSchedule;
    int sleep;
    int sleepInt;

    public AlarmFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(rootView.getContext());
        String temp = sharedPrefs.getString("prefSchedule", "0");
        perfsDefaultSchedule = Integer.parseInt(temp);

//        sleepInt = sharedPrefs.getInt("SLEEPINT", 0);
//        updateIntSleep(sleepInt);

//        saveData("psbdata.txt", "test", rootView.getContext());

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
//        int defaultSchedule = preferences.getInt("SCHEDULE", 0);


        spinner = (Spinner)rootView.findViewById(R.id.spinner);

        final SleepNowDatabase db = new SleepNowDatabase(getActivity());



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                scheduleSelected = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                scheduleSelected = 0;
            }
        });

        spinner.setSelection(perfsDefaultSchedule-1);

        setCustom = (ImageButton) rootView.findViewById(R.id.setCustom);
        setCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmIntent = new Intent(rootView.getContext(),AlarmActivity.class);
                getActivity().startActivity(alarmIntent);
            }
        });

        sleepNow = (ImageButton) rootView.findViewById(R.id.sleepNow);

        sleepNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sleepInt = sleepInt + 1;
//                updateIntSleep(sleepInt);
//                Sleep sleep = new Sleep();
//                sleep.setNum(sleepInt);
//                Calendar time = Calendar.getInstance();
//
//                sleep.setTime(time.getTime().toString());
//                db.addSleep(sleep);

                Toast.makeText(rootView.getContext(), "Old Alarms Deleted!", Toast.LENGTH_SHORT).show();
                alarmIntent2 = new Intent(rootView.getContext(),AlarmActivity.class);
                if(scheduleSelected > 0){
                    Intent sleepIntent = new Intent(rootView.getContext(), AlarmActivity.class);
                    sleepIntent.putExtra("SLEEP", scheduleSelected);
                    getActivity().startActivity(sleepIntent);

                }else{
                    Toast.makeText(rootView.getContext(),"Schedule Not Selected!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        setDefault = (ImageButton)rootView.findViewById(R.id.setDefault);
        setDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(rootView.getContext(), Settings.class);
                getActivity().startActivity(i);
            }
        });

        return rootView;
    }

    private void updateIntSleep(int sleep){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        if(sleep == 0){
            editor.putInt("SLEEPINT",0);
            editor.apply();
        }else{
            preferences.edit().putInt("SLEEPINT", sleep).apply();
        }
        sleepInt = preferences.getInt("SLEEPINT", 0);

    }


}
