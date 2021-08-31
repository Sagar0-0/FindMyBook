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
    private BookAdapter mAdapter;
    private final String GOOGLE_BOOKS_HTTP_STRING="https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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



//        searchbutton=findViewById(R.id.search);
//        loading=findViewById(R.id.loading);
//        loading.setVisibility(View.GONE);


    }

    public void searchbutton(View view){
        String key;
        EditText text=findViewById(R.id.edittext);
        key=text.getText().toString();

        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=cm.getActiveNetworkInfo();

        if(info!=null && info.isConnected()){
            BookAsyncTask task=new BookAsyncTask();
            String LOAD=GOOGLE_BOOKS_HTTP_STRING+"{"+key+"}";
            task.execute(LOAD);

            searchbutton.setBackgroundResource(0);
            loading.setVisibility(View.VISIBLE);
        }
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
            }

            TextView textView = findViewById(R.id.empty);
            textView.setVisibility(View.GONE);
        }
    }



}