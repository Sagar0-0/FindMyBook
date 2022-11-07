package com.example.android.findmybook;

import android.os.Bundle;

import androidx.activity.ComponentActivity;

public class MainActivity extends ComponentActivity {

    private final static String GOOGLE_BOOKS_HTTP_STRING = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}