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
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.XLabels;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


/**
 * Created by Mike Clarke 19/08/2014.
 *
 */
public class HomeFrag extends Fragment implements OnChartValueSelectedListener {

    private AdView adView;

    private static final String AD_UNIT_ID = "ca-app-pub-9338557771855206/7570970178";

    TextView userText;
    String user;
    String currentSchedule;
    private LineChart mChart;
    int sleepInt;



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




//        SleepNowDatabase db = new SleepNowDatabase(getActivity());
//        List<Sleep> sleeps = new LinkedList<Sleep>();
//
//        sleeps = db.getAllSleeps();

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



        mChart = (LineChart) rootView.findViewById(R.id.chart);

        // create a color template for one dataset with only one color
        ColorTemplate ct = new ColorTemplate();
        // ct.addColorsForDataSets(new int[] {
        // R.color.colorful_1
        // }, this);
        ct.addDataSetColors(new int[] {
                R.color.colorful_4
        }, rootView.getContext());

        mChart.setOnChartValueSelectedListener(this);
        mChart.setColorTemplate(ct);

        // if enabled, the chart will always start at zero on the y-axis
        mChart.setStartAtZero(false);

        // disable the drawing of values into the chart
        mChart.setDrawYValues(false);

        mChart.setLineWidth(1f);
        mChart.setCircleSize(4f);

        mChart.setDrawBorder(true);
        mChart.setBorderStyles(new BarLineChartBase.BorderStyle[] { BarLineChartBase.BorderStyle.BOTTOM });

        // no description text
        mChart.setDescription("Sleep Now Times");

        // // enable / disable grid lines
        // mChart.setDrawVerticalGrid(false);
        // mChart.setDrawHorizontalGrid(false);
        //
        // // enable / disable grid background
        // mChart.setDrawGridBackground(false);
        //
        // mChart.setDrawXLegend(false);
        // mChart.setDrawYLegend(false);

        // set the number of y-legend entries the chart should have
        mChart.setYLabelCount(24);

        // enable value highlighting
        mChart.setHighlightEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
//        mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(rootView.getContext(), R.layout.custom_marker_view);

        // define an offset to change the original position of the marker
        // (optional)
        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        // set the marker to the chart
        mChart.setMarkerView(mv);

        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        mChart.setHighlightIndicatorEnabled(false);

        // set the line to be drawn like this "- - - - - -"
        mChart.enableDashedLine(10f, 5f, 0f);

        // add data
        setData(1);



//        // restrain the maximum scale-out factor
//        mChart.setScaleMinima(3f, 3f);
//
//        // center the view to a specific position inside the chart
//        mChart.centerViewPort(10, 50);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
//        l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // dont forget to refresh the drawing
        mChart.invalidate();


        return rootView;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                if (mChart.isDrawYValuesEnabled())
                    mChart.setDrawYValues(false);
                else
                    mChart.setDrawYValues(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.isHighlightEnabled())
                    mChart.setHighlightEnabled(false);
                else
                    mChart.setHighlightEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleFilled: {
                if (mChart.isDrawFilledEnabled())
                    mChart.setDrawFilled(false);
                else
                    mChart.setDrawFilled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                if (mChart.isDrawCirclesEnabled())
                    mChart.setDrawCircles(false);
                else
                    mChart.setDrawCircles(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleStartzero: {
                if (mChart.isStartAtZeroEnabled())
                    mChart.setStartAtZero(false);
                else
                    mChart.setStartAtZero(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAdjustXLegend: {
                XLabels xLabels = mChart.getXLabels();

                if (xLabels.isAdjustXLabelsEnabled())
                    xLabels.setAdjustXLabels(false);
                else
                    xLabels.setAdjustXLabels(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleFilter: {

                // the angle of filtering is 35°
                Approximator a = new Approximator(Approximator.ApproximatorType.DOUGLAS_PEUCKER, 35);

                if (!mChart.isFilteringEnabled()) {
                    mChart.enableFiltering(a);
                } else {
                    mChart.disableFiltering();
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionDashedLine: {
                if (!mChart.isDashedLineEnabled()) {
                    mChart.enableDashedLine(10f, 5f, 0f);
                } else {
                    mChart.disableDashedLine();
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                if(mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                    Toast.makeText(getView().getContext(), "Saving SUCCESSFUL!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getView().getContext(), "Saving FAILED!", Toast.LENGTH_SHORT).show();

//                 mChart.saveToGallery("title"+System.currentTimeMillis())
                break;
            }
        }
        return true;
    }

    @Override
    public void onValuesSelected(Entry[] values, Highlight[] highlights) {
        Log.i("VALS SELECTED",
                "Value: " + values[0].getVal() + ", xIndex: " + highlights[0].getXIndex()
                        + ", DataSet index: " + highlights[0].getDataSetIndex()
        );
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }


    private void setData(int e) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            xVals.add("hit");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < 2; i++) {
            float mult = (1 + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(24f, i));
        }

        // create a dataset and give it a type
        DataSet set1 = new DataSet(yVals, "Sleep Now Hit");

        ArrayList<DataSet> dataSets = new ArrayList<DataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        ChartData data = new ChartData(xVals, dataSets);

        // set data
        mChart.setData(data);
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
