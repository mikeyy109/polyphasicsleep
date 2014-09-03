package com.liquications.polyphasicsleep;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

public class WebView extends Fragment {

    android.webkit.WebView webView;
    String url;
//    private static final String AD_UNIT_ID = "ca-app-pub-9338557771855206/7570970178";
//    private AdView adView;
    Bundle extras;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_web_view, container, false);

        Bundle extras = this.getArguments();

        url = extras.getString("URL");

//        adView = new AdView(rootView.getContext());
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId(AD_UNIT_ID);
//
//        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linearLayout);
//        layout.addView(adView);
//
//        AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder()
////                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
////                .addTestDevice("4C02A15960B9C18195E90B96F69DBC8E")
//                .build();
//
//        adView.loadAd(adRequest);


        webView = (android.webkit.WebView)rootView.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);

        return rootView;
    }


}
