package com.example.nikos.unipiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import java.util.List;

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
                .baseUrl(NewsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Interface
        NewsApi newsApi = retrofit.create(NewsApi.class);

        newsApi.getNews().enqueue(new Callback<Newsbeta>() {
            @Override
            public void onResponse(Call<Newsbeta> call, Response<Newsbeta> response) {
                if (response.isSuccessful()) {
                    Newsbeta news = response.body();
                    Log.i("News:", news.getStatus());
                } else {
                    Log.e("Failed:", "das" + response.code());
                }
            }

            @Override
            public void onFailure(Call<Newsbeta> call, Throwable t) {
                Log.e("Failed error", "dasdasdsada"+t.getMessage());
            }
        });
        //Call object
//        Call<News> call = newsApi.getNews();
//
//        //make the call
//        call.enqueue(new Callback<News>() {
//            @Override
//            public void onResponse(Call<News> call, Response<News> response) {
//                Log.d("NUmber","fafa");
//
//                response.body();
//                String title = response.body().getStatus();
//
//            Log.d("status", title);
//            }
//
//            @Override
//            public void onFailure(Call<Newsbeta> call, Throwable t) {
//                Log.d("NUmber","den to thelo");
//            }
//        });


//        call.enqueue(new Callback<Newsbeta>() {
//            @Override
//            public void onResponse(Callback<Newsbeta> call, Response<Newsbeta> response) {
//                Log.d("mpainei","nai");
//
//                List<Newsbeta> newsList = (List<Newsbeta>) response.body();
//
//                String[] news = new String[newsList.size()];
//
//                for (int i = 0; i< newsList.size(); i++) {
//                    news[i] = newsList.get(i).getStatus();
//                }
//
//                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,news));
//            }
//
//            @Override
//            public void onFailure(Callback<Newsbeta> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.d("error",t.getMessage());
//            }
//        });
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
