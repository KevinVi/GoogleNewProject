package com.exemple.projetgooglenews.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.exemple.projetgooglenews.R;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by leolebogoss on 30/01/2016.
 */
public class ImageLoader extends AsyncTask<Object, Void, Bitmap> {

    ImageView img;
    Context mContext;
    ProgressBar bar;

    public ImageLoader(ProgressBar b,ImageView img,Context ctx) {
        this.img = img;
        mContext=ctx;
        bar = b;
    }

    @Override
    protected Bitmap doInBackground(Object... data) {
        if (data.length > 0) {
            String url;
            Bitmap bitmap;
            try {

                url = String.valueOf(data[0]);
                bitmap = BitmapFactory.decodeStream(new URL(url)
                        .openConnection()
                        .getInputStream());
                if (data.length > 1) {
                    // TODO: 16/11/2015 Check this
                    @SuppressWarnings("unchecked")
                    HashMap<String, SoftReference<Bitmap>> hashMap = (HashMap<String, SoftReference<Bitmap>>) data[1];
                    hashMap.put(url, new SoftReference<>(bitmap));
                }
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                Drawable defaultDrawable = mContext.getResources().getDrawable(R.drawable.default_picture);
                if (defaultDrawable != null) {
                    bitmap = ((BitmapDrawable) defaultDrawable).getBitmap();
                    float ratioHeight = ((float) bitmap.getHeight() / (float) bitmap.getWidth()) * 400f;
                    bitmap = Bitmap.createScaledBitmap(bitmap, 400, (int) ratioHeight, false);
                    return bitmap;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap btm) {
        if (btm!=null) {
//            bar.setVisibility(View.GONE);
            img.setImageBitmap(btm);
        }
    }
}
