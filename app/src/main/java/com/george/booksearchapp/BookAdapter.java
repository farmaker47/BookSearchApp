package com.george.booksearchapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class BookAdapter extends ArrayAdapter<Book> {

    private final Context mContext;

    public BookAdapter(Context context, List<Book> bookes) {
        super(context, 0, bookes);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_info_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.titleText);
        titleTextView.setText(currentBook.getTitle());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.dateText);
        dateTextView.setText(currentBook.getDate());

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.authorText);
        authorTextView.setText(currentBook.getAuthor());

        TextView dummyTitleText = (TextView) listItemView.findViewById(R.id.dummyTitleText);
        dummyTitleText.setText(mContext.getResources().getString(R.string.dummyTitleText));

        TextView dummyDateText = (TextView) listItemView.findViewById(R.id.dummyDateText);
        dummyDateText.setText(mContext.getResources().getString(R.string.dummyDateText));

        TextView dummyAuthorText = (TextView) listItemView.findViewById(R.id.dummyAuthorText);
        dummyAuthorText.setText(mContext.getResources().getString(R.string.dummyAuthorText));

        return listItemView;
    }
}
