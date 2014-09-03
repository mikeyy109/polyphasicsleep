package com.liquications.polyphasicsleep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class InfoFrag extends Fragment {

    private AdView adView;
    private android.webkit.WebView webView;

    public String url;
    int imgUrl;
    int pageNum;

    private static final String AD_UNIT_ID = "ca-app-pub-9338557771855206/7570970178";


    public InfoFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);


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
        url = "http://www.polyphasicsociety.com/polyphasic-sleep/beginners/";
        webView = (android.webkit.WebView)rootView.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);

        return rootView;
    }


}
