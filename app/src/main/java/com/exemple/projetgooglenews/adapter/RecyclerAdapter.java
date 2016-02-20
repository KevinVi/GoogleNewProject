package com.exemple.projetgooglenews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exemple.projetgooglenews.R;
import com.exemple.projetgooglenews.model.Data;
import com.exemple.projetgooglenews.tools.ImageLoader;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by leolebogoss on 20/02/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<String, SoftReference<Bitmap>> mHashMap;
    ArrayList<Data> mData;


    public RecyclerAdapter(ArrayList<Data> data, Context ctx) {
        mData = data;
        mContext = ctx;
        mHashMap = new HashMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try{
            holder.getTitle().setText(mData.get(position).getTitle());
            holder.getContent().setText(mData.get(position).getContent());
            if (mHashMap.containsKey(mData.get(position).getImg())) {
                SoftReference<Bitmap> ref = mHashMap.get(mData.get(position).getImg());
                Bitmap bitmap = ref.get();
                if (bitmap != null) {
                    holder.progressRcycler.setVisibility(View.GONE);
                    holder.getImg().setImageBitmap(bitmap);
                } else {
                    holder.progressRcycler.setVisibility(View.VISIBLE);
                    new ImageLoader(holder.progressRcycler,holder.getImg(),mContext)
                            .execute(mData.get(position).getImg(),
                                    mHashMap);
                }
            }else{
                holder.progressRcycler.setVisibility(View.VISIBLE);
                new ImageLoader(holder.progressRcycler,holder.getImg(),mContext)
                        .execute(mData.get(position).getImg(),
                                mHashMap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return mData.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView card;
        private final TextView title;
        private final TextView content;
        private final ImageView img;
        private final ProgressBar progressRcycler;

        public ViewHolder(View itemView) {
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_row);
            title = (TextView) itemView.findViewById(R.id.title_row);
            content = (TextView) itemView.findViewById(R.id.content_row);
            img = (ImageView) itemView.findViewById(R.id.image_row);
            progressRcycler = (ProgressBar) itemView.findViewById(R.id.progressRcycler);
        }

        public CardView getCard() {
            return card;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getContent() {
            return content;
        }

        public ImageView getImg() {
            return img;
        }

        public ProgressBar getProgressRcycler() {
            return progressRcycler;
        }
    }
}
