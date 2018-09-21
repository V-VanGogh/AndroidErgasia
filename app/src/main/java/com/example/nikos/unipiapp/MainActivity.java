package com.example.nikos.unipiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listNews);

        getNews();
    }

    private  void getNews() {
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
                    Log.i("Articles:", news.getArticles().get(1).getTitle().toString());
                } else {
                    Log.e("Failed:", "das" + response.code());
                }
            }

            @Override
            public void onFailure(Call<NewsDataModel> call, Throwable t) {
                Log.e("Failed error", "dasdasdsada"+t.getMessage());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drop_down_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main){
            startActivity(new Intent(this,MainActivity.class));
        }
        else if(item.getItemId() == R.id.profile){
            startActivity(new Intent(this,ProfileActivity.class));
        }
        else if(item.getItemId() == R.id.todo1){
            startActivity(new Intent(this,MainActivity.class));
        }
        else if(item.getItemId() == R.id.signout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent logoutIntentDropDown = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(logoutIntentDropDown);
    }


}
