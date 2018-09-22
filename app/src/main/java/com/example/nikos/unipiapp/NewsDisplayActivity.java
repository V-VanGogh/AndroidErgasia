package com.example.nikos.unipiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class NewsDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<NewsDataModel>  a = this.getIntent().getExtras().getParcelableArrayList("ARRAYLIST");

//        ArrayList<Object> object = (ArrayList<Object>) args.getSerializable("ARRAYLIST");
    }
}
