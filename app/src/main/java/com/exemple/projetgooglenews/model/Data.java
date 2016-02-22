package com.exemple.projetgooglenews.model;

import android.text.Html;

import java.io.Serializable;


/**
 * Created by kevin on 12/01/2016.
 */
public class Data implements Serializable {
    public static String title;
    public static String content;
    public static String editor;
    public static String date;
    public static String img;
    public static String unescapedUrl;


    public Data(String title, String content, String editor, String date, String img, String url) {
        this.content = Html.fromHtml(content).toString();
        this.title = Html.fromHtml(title).toString();
        this.editor = editor;
        this.date = date;
        this.img = img;
        this.unescapedUrl = url;
    }
    public Data() {
    }

    public String getTitle() {
        return title;
    }

    public static void setTitle(String titre) {
        title = Html.fromHtml(titre).toString();
    }

    public String getEditor() {
        return editor;
    }

    public static void setEditor(String edit) {
        editor = edit;
    }

    public String getDate() {
        return date;
    }

    public static void setDate(String dt) {
        date = dt;
    }

    public String getImg() {
        return img;
    }

    public static void setImg(String image) {
        img = image;
    }

    public String getContent() {
        return content;
    }

    public static void setContent(String ct) {
        content = Html.fromHtml(ct).toString();
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public static void setUnescapedUrl(String Url) {
        unescapedUrl = Url;
    }

    @Override
    public String toString() {
        return "Data{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", editor='" + editor + '\'' +
                ", date='" + date + '\'' +
                ", img='" + img + '\'' +
                ", unescapedUrl='" + unescapedUrl + '\'' +
                '}';
    }
}
