package com.exemple.projetgooglenews.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exemple.projetgooglenews.R;
import com.exemple.projetgooglenews.database.Database;
import com.exemple.projetgooglenews.model.Data;
import com.exemple.projetgooglenews.tools.ImageLoader;

import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView title , content, link;
    ProgressBar bar;
    ImageView img;
    Button fav,invis,share;
    Data mData;private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Context mContext;
    private Database news_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mContext = getApplicationContext();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {


                    case R.id.home:
                        Intent it = new Intent(mContext, HomeActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(it);
                        return true;

                    case R.id.favoris:

                        Intent i = new Intent(mContext, ListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        return true;
                    case R.id.search:
                        Intent in = new Intent(mContext, MainActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(in);
                        return true;
                    case R.id.settings:

                        Intent intent = new Intent(mContext, SettingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        news_db = new Database(this);
        title = (TextView)findViewById(R.id.title_detail);
        content= (TextView)findViewById(R.id.content_detail);
        link = (TextView) findViewById(R.id.link_web);
        img = (ImageView) findViewById(R.id.img_detail);
        bar = (ProgressBar) findViewById(R.id.progressDetail);
        fav = (Button) findViewById(R.id.add_fav);
        invis = (Button) findViewById(R.id.remove_list);
        fav.setOnClickListener(this);
        invis.setOnClickListener(this);
        share = (Button) findViewById(R.id.share);
        share.setOnClickListener(this);

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
        try {
            new ImageLoader(bar,img,getApplicationContext()).execute(mData.getImg());

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_fav:
                if(fav.getText().equals(getString(R.string.favoris))){
                    fav.setText(getString(R.string.r_favoris));
                    news_db.setFavoris(mData.getUnescapedUrl());
                }else{
                    fav.setText(getString(R.string.favoris));
                    news_db.removeFavoris(mData.getUnescapedUrl());
                }
                break;
            case R.id.remove_list:
                 news_db.setVisibility(mData.getUnescapedUrl());
                break;
            case R.id.share:
                share = (Button) findViewById(R.id.share);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mData.getTitle());
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mData.getUnescapedUrl());
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    }
                });
                break;
        }
    }
}