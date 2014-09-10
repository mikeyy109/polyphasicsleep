package com.liquications.polyphasicsleep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class ScheduleFrag extends Fragment {

    private ListView polyList;
    private PolyAdapter polyArrayAdapter;



    Poly[] myPolyArray = new Poly[]{
            new Poly("Monophasic", "7-8 Hours per day", "monophasic", "Most common sleep schedule"),
            new Poly("Segmented", "6-8 Hours per day", "segmented", "Most natural"),
            new Poly("Siesta", "5-6 Hour sleep, 20-90 Min nap", "siesta", "Common in spain"),
            new Poly("Triphasic", "4-5 Hours per day", "triphasic", "Efficient and simple"),
            new Poly("Everyman", "Typically 3-4 Hours with 2-3 Naps", "everyman", "The most successful"),
            new Poly("Dual Core", "Two cores totaling about 4-5 hours", "dualcore", "Theoretically very healthy"),
            new Poly("Uberman", "6-8 Naps per day", "uberman", "Most attempted and most failed of all"),
            new Poly("Dymaxion", "4 Naps of 30 Mins", "dymaxion", "Even greater difficulty than Uberman"),
            new Poly("SPAMAYL", "7-10 Naps per day", "spamayl", "Ubermans younger cousin")

    };


    public ScheduleFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        polyList = (ListView)rootView.findViewById(R.id.listView);

        polyArrayAdapter = new PolyAdapter(rootView.getContext(), R.layout.row, myPolyArray);

        if(polyList != null){
            polyList.setAdapter(polyArrayAdapter);
        }


        polyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                schedule_detailview f = new schedule_detailview();
                switch(i){
                    case 0:
                        bundle.putInt("WHICH", 1);
                        f.setArguments(bundle);
                        break;

                    case 1:
                        bundle.putInt("WHICH", 2);
                        f.setArguments(bundle);
                        break;
                    case 2:
                        bundle.putInt("WHICH", 3);
                        f.setArguments(bundle);
                        break;
                    case 3:
                        bundle.putInt("WHICH", 4);
                        f.setArguments(bundle);
                        break;
                    case 4:
                        bundle.putInt("WHICH", 5);
                        f.setArguments(bundle);
                        break;
                    case 5:
                        bundle.putInt("WHICH", 6);
                        f.setArguments(bundle);
                        break;
                    case 6:
                        bundle.putInt("WHICH", 7);
                        f.setArguments(bundle);
                        break;
                    case 7:
                        bundle.putInt("WHICH", 8);
                        f.setArguments(bundle);
                        break;
                    case 8:
                        bundle.putInt("WHICH", 9);
                        f.setArguments(bundle);
                        break;
                    default:
                        bundle.putInt("WHICH", 1);
                        f.setArguments(bundle);
                        break;
                }
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frameLayout, f);

                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

            }
        });

        return rootView;
    }
}
