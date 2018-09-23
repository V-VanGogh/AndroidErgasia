package com.example.nikos.unipiapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsDisplayActivity extends AppCompatActivity {


    TextView titleView;
    TextView descriptionView;
    TextView nameView;
    ImageView imageView;
    Context context;
    ImageView favorite, favoritetrue;
    List<String> newslist;

    String title;
    String content;
    String name;
    String urlImage;
    FirebaseAuth mAuth;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);


        mAuth = FirebaseAuth.getInstance();


        //--------- Start Cases ---------//

        //First Case
        title = this.getIntent().getExtras().getString("TITLE");
        content = this.getIntent().getExtras().getString("CONTENT");
        name = this.getIntent().getExtras().getString("NAME");
        urlImage = this.getIntent().getExtras().getString("URLIMAGE");

        //Second Case
//        List<Article> article = this.getIntent().getExtras().getParcelableArrayList("Articles");
//        int position = this.getIntent().getExtras().getInt("Position");
//
//        title = article.get(position).getTitle();
//        content = article.get(position).getContent();
//        name = article.get(position).getSource().getName();
//        urlImage = article.get(position).getUrlToImage();

        //--------- Stop Cases ---------//


        // Use Content
        Log.d("MessegeTitle", "Set" + title);
        titleView = findViewById(R.id.txtTitleNewsDetailsActivity);
        titleView.setText(title);

        Log.d("MessegeContent", "Set" + content);
        descriptionView = findViewById(R.id.txtContentNewsDetailsActivity);
        descriptionView.setText(content);

        Log.d("MessegeName", "Set" + name);
        nameView = findViewById(R.id.txtSourceNewsDetailsActivity);
        nameView.setText(name);

        Log.d("MessegeUrlImage", "Set" + urlImage);
        imageView = findViewById(R.id.imgNewsDetailsActivity);
        Picasso.with(context).load(urlImage).into(imageView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Favorites/").child(mAuth.getUid());

        favorite = (ImageView) findViewById(R.id.imgFavoriteNot);
        favoritetrue = (ImageView) findViewById(R.id.imgFavorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFavorite(title, content, name, urlImage);

                favorite.setVisibility(View.GONE);
                favoritetrue.setVisibility(View.VISIBLE);


            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            android.content.Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FavoriteNews", "showData:");
                // Get Post object and use the values to update the UI
                DataSnapshot post = dataSnapshot;
                FavoriteNewsInformation dataretrieval = post.child("-LN6uCiItf9P0mT_ELzP").getValue(FavoriteNewsInformation.class);
                String stokalo = dataretrieval.getContent().toString();
                // [START_EXCLUDE]
                Log.d("FavoriteNews", stokalo );
                // [END_EXCLUDE]
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ERROR_RETRIEVE", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(NewsDisplayActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        });


    }


    public void addFavorite(String title, String content, String name, String urlImage) {

        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("title", title);
        taskMap.put("content", content);
        taskMap.put("source", name);
        taskMap.put("urlImage", urlImage);
        myRef.push().setValue(taskMap);


        Log.i("patima", "addFavorite: PUSS");


    }


}


