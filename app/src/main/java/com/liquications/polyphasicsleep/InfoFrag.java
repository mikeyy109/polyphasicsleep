package com.liquications.polyphasicsleep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;



/**
 * Created by Mike Clarke 19/08/2014.
 *
 */
public class InfoFrag extends Fragment {

    private android.webkit.WebView webView;

    public String url;
    int imgUrl;
    int pageNum;



    public InfoFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        url = "http://www.polyphasicsociety.com/polyphasic-sleep/beginners/";
        webView = (android.webkit.WebView)rootView.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);

        return rootView;
    }


}
