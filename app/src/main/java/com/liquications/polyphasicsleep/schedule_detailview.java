package com.liquications.polyphasicsleep;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class schedule_detailview extends Fragment {

    TextView lrgText;
    ImageView image;
    TextView title;
    int whichDetail;
    Button moreDetail;
    Button setSchedule;
    String url;




    public schedule_detailview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.activity_schedule_detailview, container, false);


        lrgText = (TextView) rootView.findViewById(R.id.lrgText);
        title = (TextView) rootView.findViewById(R.id.titleText);
        image = (ImageView) rootView.findViewById(R.id.imageView);
        moreDetail = (Button)rootView.findViewById(R.id.moreDetailBtn);





        Bundle extras = this.getArguments();

        whichDetail = extras.getInt("WHICH");




        switch(whichDetail){
            case 1:
                title.setText(getResources().getString(R.string.title_activity_monophasic));
                image.setImageResource(R.drawable.monophasic);
                lrgText.setText(getResources().getString(R.string.monophasicLrg));
                break;
            case 2:
                title.setText(getResources().getString(R.string.title_activity_segmented));
                image.setImageResource(R.drawable.segmented);
                lrgText.setText(getResources().getString(R.string.segmentedLrg));
                break;
            case 3:
                title.setText(getResources().getString(R.string.title_activity_siesta));
                image.setImageResource(R.drawable.siesta);
                lrgText.setText(getResources().getString(R.string.siestaLrg));
                break;
            case 4:
                title.setText(getResources().getString(R.string.title_activity_triphasic));
                image.setImageResource(R.drawable.triphasic);
                lrgText.setText(getResources().getString(R.string.triphasicLrg));
                break;
            case 5:
                title.setText(getResources().getString(R.string.title_activity_everyman));
                image.setImageResource(R.drawable.everyman);
                lrgText.setText(getResources().getString(R.string.everymanLrg));
                break;
            case 6:
                title.setText(getResources().getString(R.string.title_activity_dualcore));
                image.setImageResource(R.drawable.dualcore);
                lrgText.setText(getResources().getString(R.string.dualcoreLrg));
                break;
            case 7:
                title.setText(getResources().getString(R.string.title_activity_uberman));
                image.setImageResource(R.drawable.uberman);
                lrgText.setText(getResources().getString(R.string.ubermanLrg));
                break;
            case 8:
                title.setText(getResources().getString(R.string.title_activity_dymaxion));
                image.setImageResource(R.drawable.dymaxion);
                lrgText.setText(getResources().getString(R.string.dymaxionLrg));
                break;
            case 9:
                title.setText(getResources().getString(R.string.title_activity_spamayl));
                image.setImageResource(R.drawable.spamayl);
                lrgText.setText(getResources().getString(R.string.spamaylLrg));
                break;
            default:
                break;
        }

        moreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                WebView f = new WebView();
                if(whichDetail != 0){
                    switch(whichDetail){
                        case 1:
                            url = "http://www.polyphasicsociety.com/polyphasic-sleep/overviews/monophasic/";
                            break;
                        case 2:
                            url = "http://www.polyphasicsociety.com/polyphasic-sleep/overviews/segmented-sleep/";
                            break;
                        case 3:
                            url = "http://www.polyphasicsociety.com/polyphasic-sleep/overviews/siesta/";
                            break;
                        case 4:
                            url = "http://www.polyphasicsociety.com/polyphasic-sleep/overviews/triphasic/";
                            break;
                        case 5:
                            url = "http://www.polyphasicsociety.com/polyphasic-sleep/overviews/everyman/";
                            break;
                        case 6:
                            url = "http://www.polyphasicsociety.com/polyphasic-sleep/overviews/dual-core/";
                            break;
                        case 7:
                            url = "http://www.polyphasicsociety.com/polyphasic-sleep/overviews/uberman-2/";
                            break;
                        case 8:
                            url = "http://www.polyphasicsociety.com/polyphasic-sleep/overviews/dymaxion/";
                            break;
                        case 9:
                            url = "http://www.polyphasicsociety.com/polyphasic-sleep/overviews/uberman/";
                            break;
                    }

                    bundle.putString("URL", url);
                    f.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    transaction.replace(R.id.frameLayout, f);

                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                }
            }
        });




        return rootView;
    }



}
