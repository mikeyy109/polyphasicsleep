package com.liquications.polyphasicsleep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * Created by Mike Clarke 19/08/2014.
 *
 */
public class StatsFrag extends Fragment {

    String testSleepData;


    public StatsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        TextView text = (TextView)rootView.findViewById(R.id.lrgText);

        AlarmFrag af = new AlarmFrag();
        testSleepData = af.getSleepData(rootView.getContext());
        text.setText(testSleepData);
        return rootView;
    }


}
