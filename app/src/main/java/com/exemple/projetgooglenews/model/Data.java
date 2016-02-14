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


    public Data(String title, String content, String editor, String date, String img, String url) {
        this.content = Html.fromHtml(content).toString();
        this.title = Html.fromHtml(title).toString();
        this.editor = editor;
        this.date = date;
        this.img = img;
        this.unescapedUrl = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Html.fromHtml(title).toString();
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = Html.fromHtml(content).toString();
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public void setUnescapedUrl(String unescapedUrl) {
        this.unescapedUrl = unescapedUrl;
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
