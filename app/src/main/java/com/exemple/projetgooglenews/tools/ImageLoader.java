package com.exemple.projetgooglenews.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 * Created by leolebogoss on 30/01/2016.
 */
public class ImageLoader extends AsyncTask<Object, Void, Bitmap> {

    ImageView img;

    public ImageLoader(ImageView img) {
        this.img = img;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        String url = (String) params[0];
        try {
            InputStream in = new URL(url).openStream();
            return BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            // log error
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        img.setImageResource(android.R.drawable.ic_lock_lock);
    }

    @Override
    protected void onPostExecute(Bitmap btm) {
        img.setImageBitmap(btm);
    }
}
