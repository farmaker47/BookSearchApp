package com.george.booksearchapp;

/**
 * Created by farmaker1 on 15/05/2017.
 */

public class Book {
    private String mAuthor;
    private String mTitle;
    private String mDate;

    public Book(String title,String date,String author) {
        mTitle= title;
        mAuthor = author;
        mDate = date;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;

    }


    public String getDate() {
        return mDate;

    }

}
