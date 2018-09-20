package com.example.nikos.unipiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private ListView bookListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookListView = (ListView) findViewById(R.id.list1);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://openlibrary.org/authors/OL1A.json")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();





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
