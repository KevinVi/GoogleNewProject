package com.exemple.projetgooglenews.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exemple.projetgooglenews.R;
import com.exemple.projetgooglenews.activity.DetailActivity;
import com.exemple.projetgooglenews.model.Data;
import com.exemple.projetgooglenews.tools.ImageLoader;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by kevin on 12/01/2016.
 */
public class ListAdapter extends ArrayAdapter<Data> {

    private ArrayList<String> list;
    private HashMap<String, SoftReference<Bitmap>> mHashMap;


    public ListAdapter(Context context, ArrayList<Data> items) {
        super(context, 0, items);
        mHashMap = new HashMap<>();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_adapter, null);
        }

        Data p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.number_list);
            TextView tt2 = (TextView) v.findViewById(R.id.content_list);
            ImageView img = (ImageView) v.findViewById(R.id.image_list);
            ProgressBar bar = (ProgressBar) v.findViewById(R.id.progressRcycler);

            if (tt1 != null) {
                tt1.setText(p.title);
            }

            if (tt2 != null) {
                tt2.setText(p.content);
            }
            if (mHashMap.containsKey(p.img)) {
                SoftReference<Bitmap> ref = mHashMap.get(p.img);
                Bitmap bitmap = ref.get();
                if (bitmap != null) {
                    img.setImageBitmap(bitmap);
                } else {
                    new ImageLoader(bar, img, getContext())
                            .execute(p.img,
                                    mHashMap);
                }
            } else {
                new ImageLoader(bar, img, getContext())
                        .execute(p.img,
                                mHashMap);
            }


        }
        final Data mData = p;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DetailActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("mData", mData);
                Log.i("hello---", String.valueOf(args) + " | " + args.getSerializable("mData").toString());
                i.putExtra("mData", args);
                getContext().startActivity(i);
            }
        });

        return v;

    }

    @Override
    public int getCount() {
        try {
            return super.getCount();
        } catch (Exception e) {
            return 0;
        }
    }
}

