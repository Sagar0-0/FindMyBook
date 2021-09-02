package com.example.android.findmybook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loading;
    private TextView searchbutton;
    private TextView emptyView;

    private BookAdapter mAdapter;
    private final static String GOOGLE_BOOKS_HTTP_STRING="https://www.googleapis.com/books/v1/volumes?q=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emptyView = findViewById(R.id.empty);

        ListView bookListView =findViewById(R.id.list);

        mAdapter=new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book current=mAdapter.getItem(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getUrl()));
                startActivity(browserIntent);
            }
        });



        searchbutton=findViewById(R.id.search);
        loading=findViewById(R.id.loading);
        loading.setVisibility(View.GONE);


    }

    public void searchbutton(View view){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //Check if there is an active network connection to the internet
        if(networkInfo != null && networkInfo.isConnected()) {

            EditText text=findViewById(R.id.edittext);
            String key=text.getText().toString();

            BookAsyncTask task = new BookAsyncTask();
            String LOAD = GOOGLE_BOOKS_HTTP_STRING + "{" + key + "}";
            task.execute(LOAD);

            searchbutton.setBackgroundResource(0);
            loading.setVisibility(View.VISIBLE);
        }else{
            emptyView.setText("NO INTERNET CONNECTION :(");
            mAdapter.clear();
        }


        // hide keyboard once search button is clicked
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }


    private class BookAsyncTask extends AsyncTask<String,Void,List<Book>>{

        @Override
        protected List<Book> doInBackground(String... string) {
            if(string.length<1 || string[0]==null)return null;

            List<Book> result=QueryUtils.fetchBookData(string[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            loading.setVisibility(View.GONE);
            searchbutton.setBackgroundResource(R.drawable.search);

            mAdapter.clear();
            if(books!=null && !books.isEmpty()){
                mAdapter.addAll(books);

                emptyView.setText("");
            }else{
                emptyView.setText("NO SUCH BOOK FOUND!");
            }

        }
    }



}