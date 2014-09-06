package com.liquications.polyphasicsleep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.liquications.polyphasicsleep.database.Sleep;
import com.liquications.polyphasicsleep.database.SleepNowDatabase;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Mike Clarke 19/08/2014.
 *
 */
public class StatsFrag extends Fragment {


    public StatsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        TextView text = (TextView)rootView.findViewById(R.id.lrgText);
        return rootView;
    }


}
