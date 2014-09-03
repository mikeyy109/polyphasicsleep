package com.liquications.polyphasicsleep;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;


public class Main extends FragmentActivity {

    private static final int RESULT_SETTINGS = 1;

    public String name;
    public boolean notifications;
    public boolean goDark;

    private boolean ranBefore;

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUserSettings();

        if (isFirstTime()) {
            firstRunDialog();
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    public void firstRunDialog(){
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("First Run Dialog");
        Button btnClose=(Button)dialog.findViewById(R.id.cancel);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        String url = null;
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.menu_item_rate:
                url = "market://details?id=" + getPackageName();
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Couldn't launch the market", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.menu_item_report:
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText;

                String emailAddress = "liquications@gmail.com";
                String subject = "Polyphasic Sleep Bug Report";
                String body = "Issue description: ";
                body += "\n\nOther suggestions: ";

                uriText = "mailto:" + emailAddress + "?subject=" + subject + "&body=" + body;

                uriText = uriText.replace(" ", "%20");
                Uri emalUri = Uri.parse(uriText);

                send.setData(emalUri);
                startActivity(Intent.createChooser(send, "Send mail..."));

                break;
            case com.liquications.polyphasicsleep.R.id.action_settings:
                Intent i = new Intent(this, Settings.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                setUserSettings();
                break;

        }

    }

    private void setUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        name = sharedPrefs.getString("prefUsername", "NULL");

        notifications = sharedPrefs.getBoolean("allowNotifications", true);

        goDark = sharedPrefs.getBoolean("goDark", false);

        ranBefore = sharedPrefs.getBoolean("firstRun", false);



    }

    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.

            Fragment fragment = new Fragment();
            switch (position){
                case 0:
                    return fragment = new HomeFrag();
                case 1:

                    return fragment = new AlarmFrag();
                case 2:
                    return fragment = new StatsFrag();
                case 3:
                    return fragment = new ScheduleFrag();
                case 4:
                    return fragment = new InfoFrag();
//                case 5:
//                    return fragment = new Frag_nds();
                default:
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section5).toUpperCase(l);



            }
            return null;
        }




    }

}
