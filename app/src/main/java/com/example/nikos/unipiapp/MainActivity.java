package com.example.nikos.unipiapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends DropDownMenu {

    ListView listView;
    FirebaseAuth mAuth;



    ArrayList<String> newsArticles = new ArrayList<String>();
    ArrayList<String> newsArticlesImagesUrl = new ArrayList<String>();
    NewsListAdapter news_adapter;


    //Example
    ArrayList<NewsDataModel> newsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
//      img = findViewById(R.id.imageView2);
        newsArrayList= new ArrayList<>();


//        Toolbar toolbar;
        listView = findViewById(R.id.listNews);


        getNews();
//        init();
//        listView.setAdapter(news_adapter);

    }

    private void getNews() {
        // Retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Interface
        NewsInterface newsInterface = retrofit.create(NewsInterface.class);

        newsInterface.getNews().enqueue(new Callback<NewsDataModel>() {
            @Override
            public void onResponse(Call<NewsDataModel> call, Response<NewsDataModel> response) {
                if (response.isSuccessful()) {
                    NewsDataModel news = response.body();
                    Log.i("Status:", news.getStatus());
                    Log.i("TotalResult:", news.getTotalResults().toString());
                    for (int i = 0; i < news.getArticles().size(); i++) {
                       newsArrayList.add(new NewsDataModel(
                               news.getStatus(),
                               news.getTotalResults(),
                               news.getArticles()
                                              ));






                        Log.i("Articles:", news.getArticles().get(i).getTitle().toString());
                        Log.i("Articles:", news.getArticles().get(i).getUrlToImage().toString());
                        newsArticles.add(news.getArticles().get(i).getTitle().toString());
                        newsArticlesImagesUrl.add(news.getArticles().get(i).getUrlToImage().toString());


                    }
                } else {
                    Log.e("Failed:", "das" + response.code());
                }


                NewsListAdapter adapter =  new NewsListAdapter(getApplicationContext(), R.layout.listview_layout, newsArrayList);
                listView.setAdapter(adapter);





//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext(), android.R.layout.simple_list_item_1, newsArticles);
//                listView.setAdapter(adapter);

//                Picasso.with(getApplicationContext())
//                        .load("https://as01.epimg.net/baloncesto/imagenes/2018/09/10/mas_baloncesto/1536596847_456306_1536597028_noticia_normal.jpg")
//                       .into(img);


            }

            @Override
            public void onFailure(Call<NewsDataModel> call, Throwable t) {
                Log.e("Failed error", "dasdasdsada" + t.getMessage());
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

//    private void init() {
//        news_adapter = new NewsListAdapter(this,newsArticles, newsArticlesImagesUrl );
//        listView = (ListView) findViewById(R.id.listNews);
//    }


}
