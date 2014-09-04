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
    Bundle extras;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_web_view, container, false);

        Bundle extras = this.getArguments();

        url = extras.getString("URL");


        webView = (android.webkit.WebView)rootView.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);

        return rootView;
    }


}
