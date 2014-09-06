package com.liquications.polyphasicsleep;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Mike Clarke 19/08/2014.
 *
 */
public class HomeFrag extends Fragment {

    TextView userText;
    String user;
    String currentSchedule;

    public HomeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(rootView.getContext());


        user = sharedPrefs.getString("prefUsername", "-Not Set-");
        String temp1 = sharedPrefs.getString("prefSchedule", "0");
        int temp = Integer.parseInt(temp1);
        setSchedulePerf(temp);
        userText = (TextView)rootView.findViewById(R.id.userName);
        userText.setText(user + ". Current schedule: " + currentSchedule);

        return rootView;
    }

    public void setSchedulePerf(int temp){
        switch(temp){
            case 1:
                currentSchedule = "Monophasic";
                break;
            case 2:
                currentSchedule = "Segmented";
                break;
            case 3:
                currentSchedule = "Siesta";
                break;
            case 4:
                currentSchedule = "Triphasic";
                break;
            case 5:
                currentSchedule = "Everyman";
                break;
            case 6:
                currentSchedule = "Dual Core";
                break;
            case 7:
                currentSchedule = "Uberman";
                break;
            case 8:
                currentSchedule = "Dymaxion";
                break;
            case 9:
                currentSchedule = "SPAMAYL";
                break;
            case 10:
                SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getActivity().getApplicationContext());
                currentSchedule = sharedPrefs.getString("custScheduleName", "");
                break;
            default:
                currentSchedule = "-Not Set-";
        }
    }
}
