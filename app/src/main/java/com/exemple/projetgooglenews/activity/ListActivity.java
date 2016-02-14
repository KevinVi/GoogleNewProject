package com.exemple.projetgooglenews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.exemple.projetgooglenews.R;
import com.exemple.projetgooglenews.model.Data;
import com.exemple.projetgooglenews.tools.JsonRequest;

import java.util.ArrayList;

/**
 * Created by kevin on 12/01/2016.
 */
public class ListActivity extends AppCompatActivity {
    String title=null;
    ArrayList<Data> data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();

        try {
            title = intent.getStringExtra("search");
            data = (ArrayList<Data>) intent.getSerializableExtra("data");
            Log.i("-------","hello : "+title);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (title == null) {
            title = "hello";
        }

        TextView t = (TextView)findViewById(R.id.title_search);
        t.setText(title);

        final ListView listview = (ListView) findViewById(R.id.listview);
        ListAdapter adapter = new com.exemple.projetgooglenews.adapter.ListAdapter(this, data);
        ListView lv=(ListView)findViewById(R.id.listview);
        lv.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Log.i("hello", "here"+position) ;

            }

        });
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
            return true;
        }
        if(id == R.id.action_search){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
