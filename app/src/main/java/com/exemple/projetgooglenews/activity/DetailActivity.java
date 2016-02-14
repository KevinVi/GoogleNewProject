package com.exemple.projetgooglenews.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exemple.projetgooglenews.R;
import com.exemple.projetgooglenews.model.Data;
import com.exemple.projetgooglenews.tools.ImageLoader;

import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity {

    TextView title , content, link;
    ImageView img;
    Data mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView)findViewById(R.id.title_detail);
        content= (TextView)findViewById(R.id.content_detail);
        link = (TextView) findViewById(R.id.link_web);
        img = (ImageView) findViewById(R.id.img_detail);


        Intent i = getIntent();
        Bundle mBundle = i.getBundleExtra("mData");
        Log.i("hello---", String.valueOf(mBundle) + mBundle.toString() + mBundle.getSerializable("mData"));
        mData = (Data) mBundle.getSerializable("mData");
        title.setText(mData.getTitle());
        content.setText(mData.getContent());

        link.setText(mData.getUnescapedUrl());
        link.setLinksClickable(true);
        final String web_link=mData.getUnescapedUrl();
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, WebActivity.class);
                i.putExtra("link", web_link);
                startActivity(i);
            }
        });
        Bitmap btm  ;
        try {
            new ImageLoader(img).execute(mData.getImg());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,SettingActivity.class);
            startActivity(i);
        }
        if(id==R.id.action_list){
            Intent i = new Intent(this,ListActivity.class);
            startActivity(i);
            finish();
        }
        if(id == R.id.action_search){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}