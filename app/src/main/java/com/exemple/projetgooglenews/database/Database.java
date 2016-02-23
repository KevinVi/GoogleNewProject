package com.exemple.projetgooglenews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.exemple.projetgooglenews.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saman on 21/02/2016.
 */
public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GoogleNews.db";
    public static final String TABLE_NAME = "news_table";
    public static final String COL_id = "Id";
    public static final String COL_titre = "Titre";
    public static final String COL_contenu = "Contenu";
    public static final String COL_image = "Image";
    public static final String COL_url = "URL";
    public static final String COL_visible = "Visible";
    public static final String COL_date = "Date";
    public static final String COL_auteur = "Auteur";
    public static final String COL_favoris = "Favoris";
    public static final String COL_keyWord = "KeyWord";



    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Titre TEXT, Contenu TEXT, Image TEXT," +
                "URL TEXT, Visible TEXT, Date TEXT, Auteur TEXT, Favoris TEXT, KeyWord TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertNews(String titre, String contenu, String img, String url, String date, String auteur, String tag ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_titre, titre);
        contentValues.put(COL_contenu, contenu);
        contentValues.put(COL_image, img);
        contentValues.put(COL_url, url);
        contentValues.put(COL_visible, "true");
        contentValues.put(COL_date, date);
        contentValues.put(COL_auteur, auteur);
        contentValues.put(COL_favoris, "false");
        contentValues.put(COL_keyWord, tag);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public void setVisibility(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_visible, "true");
        db.update(TABLE_NAME, contentValues, "Id =" + id, null);
        db.close();
    }

    public void setFavoris(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_favoris, "true");
        db.update(TABLE_NAME, contentValues, "Id = " + id, null);
        db.close();
    }

    //TODO Max(date)
    public void getLastNews(){

        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE "+COL_date+"= 'true'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
    }

    public List<Data> getAllNews() {
        List<Data> newsList = new ArrayList<Data>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE "+COL_visible+"= 'true'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Data news = new Data(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(6), cursor.getString(7));

                newsList.add(news);
            } while (cursor.moveToNext());
        }
        return newsList;
    }

    public ArrayList<Data> getNewsViaKey(String key) {
        ArrayList<Data> newsList = new ArrayList<Data>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE "+COL_keyWord+"= '"+key+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                //Data.setFavoris("false");
                //Data.setKeyWord(cursor.getString(9));
                Data news = new Data(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(6), cursor.getString(7));
                newsList.add(news);
            } while (cursor.moveToNext());
        }
        return newsList;
    }

    public ArrayList<Data> getAllFavourites() {
        ArrayList<Data> favList = new ArrayList<Data>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE "+COL_favoris+"= 'true'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Data fav = new Data(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(6), cursor.getString(7));

                favList.add(fav);
            } while (cursor.moveToNext());
        }
        return favList;
    }
}
