package com.liquications.polyphasicsleep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Mike Clarke on 26/07/2014.
 */
public class PolyAdapter extends ArrayAdapter<Poly> {

    Context mContext;
    int mLayoutResourceId;
    Poly mData[] = null;

    public PolyAdapter(Context context, int resource, Poly[] data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mData = data;
    }

    @Override
    public Poly getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        row = inflater.inflate(mLayoutResourceId, parent, false);

        TextView nameView = (TextView) row.findViewById(R.id.nameTextView);
        TextView detailView = (TextView) row.findViewById(R.id.moreDetails);
        ImageView imageView = (ImageView) row.findViewById(R.id.imageView);

        Poly poly = mData[position];

        nameView.setText(poly.nameOfPoly);
        detailView.setText(poly.detailOfPoly);



        int resId = mContext.getResources().getIdentifier(poly.nameOfImage, "drawable", mContext.getPackageName());


        Picasso.with(getContext()).load(resId).placeholder(R.drawable.placeholder)
                .resize(100, 100).centerInside().into(imageView);



        return row;

    }


}
