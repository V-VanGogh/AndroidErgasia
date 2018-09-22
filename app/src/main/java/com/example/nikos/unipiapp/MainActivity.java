package com.example.nikos.unipiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        listView = findViewById(R.id.listNews);

        getNews();
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
                        Log.i("Articles:", news.getArticles().get(i).getTitle().toString());
                        newsArticles.add(news.getArticles().get(i).getTitle().toString());

                    }
                } else {
                    Log.e("Failed:", "das" + response.code());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext(), android.R.layout.simple_list_item_1, newsArticles);
                listView.setAdapter(adapter);
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


}
