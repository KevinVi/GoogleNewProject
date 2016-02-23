package com.exemple.projetgooglenews.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
import com.exemple.projetgooglenews.tools.JsonRequest;
import com.exemple.projetgooglenews.database.Database;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.widget.LinearLayout.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int j, k, total = 0;
    boolean test = false;
    EditText text;
    Button btn;
    String search_unsplit;
    LinearLayout ll;
    LinearLayout LL;
    int width;
    LinearLayout.LayoutParams lp;
    LinearLayout.LayoutParams parame;
    private static final int REQUEST_CODE = 10;
    private static final int BUTTON_ID = 1;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Context mContext;
    private Database News_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        News_db = new Database(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        mContext = getApplicationContext();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.favoris:
                        Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(mContext, ListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        return true;
                    case R.id.search:
                        Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.settings:
                        Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
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
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


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

                    Intent i = new Intent(getApplicationContext(), ListActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    i.putExtra("search", title);
                    i.putExtra("data", check);
                    getApplicationContext().startActivity(i);
                    finish();
                }
            });
            int buttonwidth = myButton.getText().length();
            Log.i("tag", "width" + width + "| layout width" + linearwidth + "||button" + buttonwidth);
            if (25 > total + buttonwidth + 5) {
                LL.addView(myButton, lp);
                total += buttonwidth;
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
        if (test) {
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
            finish();

        }
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btn.getId()) {
            if (!(text.getText().length() < 1)) {

                try {
                    Log.i("coucoucoucouc---", News_db.getNewsViaKey("Obama").toString());
                }catch (Exception e){
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

                ArrayList<Data> check = null;
                try {
                    check = new JsonRequest(getApplicationContext(), News_db).execute(text.getText().toString()).get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(this, ListActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("search", text.getText().toString());
                i.putExtra("data", check);
                startActivity(i);
                finish();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Search Empty", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
                    search_unsplit = prefs.getString("search", "");

                    //split
                    String[] separated = search_unsplit.split(";");


                    for (int i = 0; i < separated.length; i++) {

                        int linearwidth = LL.getWidth();

                        final Button myButton = new Button(this);
                        myButton.setText(separated[i]);
                        final String title = separated[i];
                        myButton.setLayoutParams(parame);
                        myButton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(MainActivity.this, ListActivity.class);
                                Log.i("-------", "title : " + title);
                                i.putExtra("search", title);
                                startActivity(i);
                                finish();
                            }
                        });
                        int buttonwidth = myButton.getText().length();
                        Log.i("tag", "width" + width + "| layout width" + linearwidth + "||button" + buttonwidth);
                        if (25 > total + buttonwidth + 5) {
                            LL.addView(myButton, lp);
                            total += buttonwidth;
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
                    if (test) {
                        ll.addView(LL, j);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
