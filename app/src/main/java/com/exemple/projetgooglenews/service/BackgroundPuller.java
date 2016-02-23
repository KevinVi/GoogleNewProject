package com.exemple.projetgooglenews.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.exemple.projetgooglenews.R;
import com.exemple.projetgooglenews.activity.MainActivity;
import com.exemple.projetgooglenews.database.Database;
import com.exemple.projetgooglenews.model.Data;
import com.exemple.projetgooglenews.tools.JsonRequest;
import com.exemple.projetgooglenews.tools.Tools;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class BackgroundPuller extends Service {
    private String TAG = this.getClass().getSimpleName();
    private Database news_db = new Database(this);
    private int maj;
    String [] default_recherche = {"economie","sport","france","international","culture","sante"};

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int timer;
        try{
            timer = SP.getInt("pref_query_limit", 1);
        }catch (Exception e){
            timer=1;
        }
        int time = timer* 60 * 1000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Tools.isNetworkAvailable(getApplicationContext())){
                    // verification recherche par défaut
                    //sport
                    for (String search : default_recherche) {
                        ArrayList<Data> mData = news_db.getNewsViaKey(search);
                        ArrayList<Data> sData = null;
                        try {
                            sData = new JsonRequest(getApplicationContext()).execute(search).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        if (sData != null && mData.size() > 2) {

                            Log.i("WHY", " HELLO");
                            for (Data d : sData) {

                                int i = 0;
                                for (int j = 0; j < mData.size(); j++) {
                                    if (mData.get(j).getTitle().equals(d.getTitle())) {
                                        i++;
                                    }

                                }
                                if (i == 0) {

                                    news_db.insertNews(d.getTitle(), d.getContent(), d.getImg(), d.getUnescapedUrl(), d.getDate(), d.getEditor(), search);
                                    maj++;
                                }


                            }
                        } else if (sData !=null){
                            for (Data d : sData) {
                                news_db.insertNews(d.getTitle(), d.getContent(), d.getImg(), d.getUnescapedUrl(), d.getDate(), d.getEditor(), search);
                                maj++;
                            }
                        }
                    }

                    SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
                    String search_unsplit = prefs.getString("search", "");

                    //split
                    String[] separated = search_unsplit.split(";");
                    for (String s : separated){
                        ArrayList<Data> mData = news_db.getNewsViaKey(s);
                        ArrayList<Data> sData = null;
                        try {
                            sData = new JsonRequest(getApplicationContext()).execute(s).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        if (sData != null ) {

                            Log.i("WHY", " HELLO");
                            for (Data d : sData) {

                                int i = 0;
                                for (int j = 0; j < mData.size(); j++) {
                                    if (mData.get(j).getTitle().equals(d.getTitle())) {
                                        i++;
                                    }

                                }
                                if (i == 0) {

                                    news_db.insertNews(d.getTitle(), d.getContent(), d.getImg(), d.getUnescapedUrl(), d.getDate(), d.getEditor(), s);
                                    maj++;
                                }


                            }
                        }
                    }
                    if (maj>0){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("maj", maj);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle(getApplicationContext().getString(R.string.maj))
                                .setContentText(getApplicationContext().getString(R.string.content, maj))
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);

                        NotificationManager mgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        mgr.notify(0, builder.build());
                    }


                }

            }
        }, 0, (time));
        return Service.START_STICKY;
    }

    public int getMaj() {
        return maj;
    }

    public void setMaj(int maj) {
        this.maj = maj;
    }

    @Override
    public IBinder onBind(Intent intent) {return null;
    }


}
