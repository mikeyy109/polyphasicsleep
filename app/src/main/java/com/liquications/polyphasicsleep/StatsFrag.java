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
 * A simple {@link Fragment} subclass.
 *
 */
public class StatsFrag extends Fragment {

    private AdView adView;

    private static final String AD_UNIT_ID = "ca-app-pub-9338557771855206/7570970178";


    public StatsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);


        adView = new AdView(rootView.getContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AD_UNIT_ID);

        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linearLayout);
        layout.addView(adView);

        AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("4C02A15960B9C18195E90B96F69DBC8E")
                .build();

        adView.loadAd(adRequest);

        SleepNowDatabase db = new SleepNowDatabase(getActivity());
        List<Sleep> sleeps = new LinkedList<Sleep>();

        sleeps = db.getAllSleeps();

        TextView text = (TextView)rootView.findViewById(R.id.lrgText);
        text.setText(sleeps.toString());
//        for(int i = 0; i < sleeps.size(); i++){
//            text.setText(sleeps.get(i).toString());
//        }


        return rootView;
    }


}
