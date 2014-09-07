package com.liquications.polyphasicsleep;


import android.content.Context;
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
import android.widget.Spinner;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


/**
 * Created by Mike Clarke 19/08/2014.
 *
 */
public class AlarmFrag extends Fragment {

    public static final String DATA_FILE = "polydata";

    private Spinner spinner;
    ImageButton setCustom;
    ImageButton sleepNow;
    Intent alarmIntent;
    Intent alarmIntent2;
    int scheduleSelected = 1;
    ImageButton setDefault;
    int perfsDefaultSchedule;
    String sleepData;

    public AlarmFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);

        // Get default schedule data
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(rootView.getContext());
        String temp = sharedPrefs.getString("prefSchedule", "0");
        perfsDefaultSchedule = Integer.parseInt(temp);

        // Get current sleep data
        sleepData = getSleepData(rootView.getContext());

        spinner = (Spinner)rootView.findViewById(R.id.spinner);
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
                //Get current time to save to database.
                Calendar cal = Calendar.getInstance();
                int hour = cal.HOUR;
                String temp = Integer.toString(hour);
                sleepData += temp;

                // Make toasts, Send alarm intents
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

    private void saveSleepData(String sleepData, Context ctx){
        FileOutputStream fileOut = null;
        try{
            fileOut = ctx.openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
            fileOut.write(sleepData.getBytes());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(fileOut != null){
                    fileOut.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private String getSleepData(Context ctx){
        FileInputStream fileIn = null;
        String fileCopy = null;

        try{
            fileIn = ctx.openFileInput(DATA_FILE);
            int size = fileIn.available();
            byte[] buffer = new byte[size];
            fileIn.read(buffer);
            fileIn.close();
            fileCopy = new String(buffer,"UTF-8");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(fileIn != null){
                    fileIn.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return fileCopy;
    }


}
