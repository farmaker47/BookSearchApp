package com.george.booksearchapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {
    //Initialization of Views
    private EditText mEditText;
    private Button mButton;
    private TextView mTextView;
    private ListView bookListView;
    private NestedScrollView nestedScrollView;
    //Initialization of bookadapter an Loadmanager
    private BookAdapter mAdapter;
    private LoaderManager loaderManager;

    //Making a string final so we can add later the string from the edittext
    private static final String BOOK_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?maxResults=20&q=";
    //Global string of EditText
    private String editTextString;
    //giving the loadmanager a fina value
    private static final int BOOK_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);


        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.buttonSearch);
        mTextView = (TextView) findViewById(R.id.empty_view);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScroll);

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        //Setting a listener to the Search Button
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String editstring = mEditText.getText().toString();
                editTextString = BOOK_REQUEST_URL + editstring;

                /////////////////////////
                ///////////////////////
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                // Get details on the currently active default da
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    loaderManager = getLoaderManager();


                    mAdapter.clear();
                    bookListView.setAdapter(mAdapter);
                    //Reseting the loadmanager so when we press second time the Search button to start a
                    //new search
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, ScrollingActivity.this);
                    mTextView.setVisibility(View.GONE);


                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in the whole name of activity because inside onclicklistener it cannot take as parameter only "this".
                    loaderManager.initLoader(BOOK_LOADER_ID, null, ScrollingActivity.this);
                    Log.e("there is internet", "OK");
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.VISIBLE);

                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);

                    // Update empty state with no connection error message
                    mTextView.setText(R.string.no_internet);
                }

                //Upon Button click hide the keybord
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

            }
        });


        // Find a reference to the {@link ListView} in the layout
        bookListView = (ListView) findViewById(R.id.list);
        bookListView.setEmptyView(mTextView);


        // Create a new {@link ArrayAdapter} of books
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.e("Loader", "load");
        return new BookLoader(this, editTextString);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookes) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        mTextView.setText(R.string.no_books);

        // Clear the adapter of previous book data
        mAdapter.clear();

        // If there is a valid list of books, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (bookes != null && !bookes.isEmpty()) {
            mAdapter.addAll(bookes);
            //Trying to find the height of listview depend of the height of the whole items
            //We use the special class Utility and the metod setListViewHeightBasedOnChildren
            //We set this height to the NestedScrollView
            int height = Utility.setListViewHeightBasedOnChildren(bookListView);
            nestedScrollView.setMinimumHeight(height);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }


}
