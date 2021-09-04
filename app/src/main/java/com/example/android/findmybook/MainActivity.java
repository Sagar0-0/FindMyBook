package com.example.android.findmybook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loading;
    private TextView searchbutton;
    private ImageView emptyimg;
    private TextView myName;

    private BookAdapter mAdapter;
    private final static String GOOGLE_BOOKS_HTTP_STRING="https://www.googleapis.com/books/v1/volumes?q=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emptyimg=findViewById(R.id.emptyimg);
        myName=findViewById(R.id.myname);

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
        try{

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            //Check if there is an active network connection to the internet
            if (networkInfo != null && networkInfo.isConnected()) {

                String key = "";
                EditText text = findViewById(R.id.edittext);
                key = text.getText().toString();
                if(key.isEmpty()){
                    Toast.makeText(this, "Please enter some keyword first", Toast.LENGTH_SHORT).show();
                }else{

                    BookAsyncTask task = new BookAsyncTask();
                    String LOAD = GOOGLE_BOOKS_HTTP_STRING + "{" + key + "}";
                    task.execute(LOAD);

                    searchbutton.setBackgroundResource(0);
                    loading.setVisibility(View.VISIBLE);


                    // hide keyboard once search button is clicked
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }


            } else {
                emptyimg.setImageResource(R.drawable.nointernet);
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                mAdapter.clear();
                myName.setText("Github: Sagar0-0/FindMyBook");
            }




        }catch (Exception e){}
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
            searchbutton.setBackgroundResource(R.drawable.gold);

            mAdapter.clear();
            if(books!=null && !books.isEmpty()){
                mAdapter.addAll(books);
                emptyimg.setImageResource(0);
                myName.setText("");
            }else{
                emptyimg.setImageResource(R.drawable.foundnothing);
                Toast.makeText(MainActivity.this, "No such book found", Toast.LENGTH_SHORT).show();
                myName.setText("Github: Sagar0-0/FindMyBook");
            }

        }
    }



}