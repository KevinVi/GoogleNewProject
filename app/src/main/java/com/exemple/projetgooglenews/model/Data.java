package com.exemple.projetgooglenews.model;

import android.text.Html;

import java.io.Serializable;


/**
 * Created by kevin on 12/01/2016.
 */
public class Data implements Serializable {
    public String title;
    public String content;
    public String editor;
    public String date;
    public String img;
    public String unescapedUrl;
    public String keyword;
    public Boolean favoris;
    public Boolean visible;


    public Data(String title, String content, String img, String url, String date, String editor,String search) {
        this.content = Html.fromHtml(content).toString();
        this.title = Html.fromHtml(title).toString();
        this.editor = editor;
        this.date = date;
        this.img = img;
        this.unescapedUrl = url;
        this.keyword=search;
        favoris=false;
        visible=true;
    }

    public Data() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titre) {
        title = Html.fromHtml(titre).toString();
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String edit) {
        editor = edit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dt) {
        date = dt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String image) {
        img = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String ct) {
        content = Html.fromHtml(ct).toString();
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public void setUnescapedUrl(String Url) {
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
