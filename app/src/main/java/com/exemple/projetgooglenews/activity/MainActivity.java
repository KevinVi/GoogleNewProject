package com.exemple.projetgooglenews.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.exemple.projetgooglenews.R;
import com.exemple.projetgooglenews.model.Data;
import com.exemple.projetgooglenews.service.BackgroundPuller;
import com.exemple.projetgooglenews.tools.JsonRequest;
import com.exemple.projetgooglenews.database.Database;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.widget.LinearLayout.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int j, total = 0;
    boolean test = false;
    EditText text;
    Button btn, btn_international, btn_france, btn_economie, btn_culture, btn_sport, btn_sante;
    String search_unsplit;
    LinearLayout ll;
    LinearLayout LL;
    int width;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Context mContext;
    private Database News_db;
    FloatingActionButton refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        News_db = new Database(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //btn
        btn_international = (Button) findViewById(R.id.international);
        btn_culture = (Button) findViewById(R.id.culture);
        btn_economie = (Button) findViewById(R.id.economie);
        btn_france = (Button) findViewById(R.id.france);
        btn_sante = (Button) findViewById(R.id.sante);
        btn_sport = (Button) findViewById(R.id.sport);
        refresh = (FloatingActionButton) findViewById(R.id.refresh);
        btn_france.setOnClickListener(this);
        btn_sport.setOnClickListener(this);
        btn_sante.setOnClickListener(this);
        btn_economie.setOnClickListener(this);
        btn_culture.setOnClickListener(this);
        btn_international.setOnClickListener(this);
        refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BackgroundPuller.class);
                stopService(intent);
                startService(intent);
            }
        });

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
                        Intent in = new Intent(mContext, HomeActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(in);
                        return true;


                    case R.id.favoris:

                        Intent i = new Intent(mContext, ListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        return true;
                    case R.id.search:

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
        if (getIntent().getExtras() != null) {
            int maj = getIntent().getExtras().getInt("maj");


            if (maj!=0){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                // set title
                alertDialogBuilder.setTitle(getResources().getString(R.string.maj));

                // set dialog message
                alertDialogBuilder
                        .setMessage(getResources().getString(R.string.content,maj))
                        .setCancelable(false)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        }

        text = (EditText) findViewById(R.id.text_2);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        ll = (LinearLayout) findViewById(R.id.my_test);
        width = ll.getWidth();


        LinearLayout.LayoutParams parame = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
        LL = new LinearLayout(this);
        LL.setBackgroundColor(Color.CYAN);
        LL.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LL.setLayoutParams(lp);


        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        search_unsplit = prefs.getString("search", "");

        //split
        String[] separated = search_unsplit.split(";");

        Log.i("length", separated.length + "");

        for (int i = 0; i < separated.length; i++) {

            int linearwidth = LL.getWidth();
            Log.i("sep", separated[i]);

            Button myButton = new Button(this);
            myButton.setText(separated[i]);
            final String title = separated[i];
            myButton.setMaxLines(1);
            myButton.setLayoutParams(parame);
            myButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("-------", "title : " + title);
                    ArrayList<Data> check = null;


                    check = News_db.getNewsViaKey(title);
                    //check = new JsonRequest(getApplicationContext(),News_db).execute(title).get();

                    Intent i = new Intent(getApplicationContext(), RecyclerActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    i.putExtra("search", title);
                    i.putExtra("data", check);
                    getApplicationContext().startActivity(i);
                }
            });
            int buttonwidth = myButton.getText().length();
            Log.i("tag", "width" + width + "| layout width" + linearwidth + "||button" + buttonwidth);
            if (25 > total + buttonwidth + 5) {
                LL.addView(myButton, lp);
                total += buttonwidth;
                test = true;
            } else {
                total = 0;
                ll.addView(LL, j);
                j++;
                LL = new LinearLayout(this);
                LL.setBackgroundColor(Color.CYAN);
                LL.setOrientation(LinearLayout.HORIZONTAL);
                LL.setLayoutParams(lp);
                LL.addView(myButton, lp);
                test = true;
            }
        }
        if (test && separated.length>0) {
            ll.addView(LL, j);
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
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        }
        if (id == R.id.action_list) {
            Intent i = new Intent(this, ListActivity.class);
            startActivity(i);

        }
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, RecyclerActivity.class);
        ArrayList<Data> check = null;
        switch (v.getId()) {
            case R.id.btn:
                if (!(text.getText().length() < 1)) {

                    try {
                        Log.i("coucoucoucouc---", News_db.getNewsViaKey("Obama").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
                    if (search_unsplit.length() < 1) {
                        search_unsplit = text.getText().toString();
                    } else {
                        search_unsplit += ";" + text.getText().toString();
                    }
                    Log.i("dzdzz", search_unsplit);
                    editor.putString("search", search_unsplit);
                    editor.commit();


                    try {
                        check = new JsonRequest(getApplicationContext(), News_db).execute(text.getText().toString()).get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                    i.putExtra("search", text.getText().toString());

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Search Empty", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                break;

            case R.id.sport:
                check = News_db.getNewsViaKey("sport");
                i.putExtra("search", "Sport");
                break;
            case R.id.economie:
                check = News_db.getNewsViaKey("economie");
                i.putExtra("search", "Economie");
                break;
            case R.id.france:
                check = News_db.getNewsViaKey("france");
                i.putExtra("search", "France");
                break;
            case R.id.culture:
                check = News_db.getNewsViaKey("culture");
                i.putExtra("search", "Culture");
                break;
            case R.id.sante:
                check = News_db.getNewsViaKey("sante");
                i.putExtra("search", "Santé");
                break;
            case R.id.international:
                check = News_db.getNewsViaKey("international");
                i.putExtra("search", "International");
                break;
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("data", check);
        startActivity(i);


    }


}
