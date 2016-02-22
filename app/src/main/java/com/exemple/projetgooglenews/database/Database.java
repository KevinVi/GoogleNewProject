package com.exemple.projetgooglenews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saman on 21/02/2016.
 */
public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GoogleNews.db";
    public static final String TABLE_NAME = "news_table";
    public static final String COL_titre = "Titre";
    public static final String COL_contenu = "Contenu";
    public static final String COL_image = "Image";
    public static final String COL_url = "URL";
    public static final String COL_visible = "Visible";
    public static final String COL_date = "Date";
    public static final String COL_auteur = "Auteur";

    public static final String TABLE_TAG = "tag_table";
    public static final String COL_tag = "Tag";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table" + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Titre TEXT, Contenu TEXT, Image TEXT)");
        db.execSQL("create table" +TABLE_TAG +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,Tag TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertNews(String titre, String contenu, String img, String url, String visible, String date, String auteur ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_titre, titre);
        contentValues.put(COL_contenu, contenu);
        contentValues.put(COL_image, img);
        contentValues.put(COL_url, url);
        contentValues.put(COL_visible, visible);
        contentValues.put(COL_date, date);
        contentValues.put(COL_auteur, auteur);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
}
