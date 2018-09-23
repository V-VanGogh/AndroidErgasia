package com.example.nikos.unipiapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    String title;
    String content;
    String name;
    String urlImage;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    DatabaseReference myRef2;


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
        myRef2 = database.getReference("Favorites/").child(mAuth.getUid());


        favorite = (ImageView) findViewById(R.id.imgFavoriteNot);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFavorite(title, content, name, urlImage);

                favorite.setVisibility(View.GONE);
                favoritetrue.setVisibility(View.VISIBLE);
            }
        });

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                List<String> newslist = takeData(dataSnapshot);
//                String value = dataSnapshot.getValue(String.class);
//                Log.e("listview", "onCreate: "+ value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("den Mpike", databaseError.toString());
//
//            }
//        });


//        if(newslist.contains(title)){
//            favorite.setVisibility(View.GONE);
//            favoritetrue.setVisibility(View.VISIBLE);
//        }
//        else{
//            favorite = (ImageView) findViewById(R.id.imgFavoriteNot);
//            favoritetrue = (ImageView) findViewById(R.id.imgFavorite);
//            favorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    addFavorite(title, content, name, urlImage);
//
//                    favorite.setVisibility(View.GONE);
//                    favoritetrue.setVisibility(View.VISIBLE);
//
//
//                }
//            });
//        }


    }

//    private List<String> takeData(DataSnapshot dataSnapshot) {
//        List<String> array = new ArrayList<String>();
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            FavoriteNewsInformation fnInfo = new FavoriteNewsInformation();
//            fnInfo.setContent(ds.child(mAuth.getUid()).getValue(FavoriteNewsInformation.class).getContent());
//            fnInfo.setSource(ds.child(mAuth.getUid()).getValue(FavoriteNewsInformation.class).getSource());
//            fnInfo.setTitle(ds.child(mAuth.getUid()).getValue(FavoriteNewsInformation.class).getTitle());
//            fnInfo.setUrlImage(ds.child(mAuth.getUid()).getValue(FavoriteNewsInformation.class).getUrlImage());
//            array.add(fnInfo.getContent());
//            array.add(fnInfo.getSource());
//            array.add(fnInfo.getTitle());
//            array.add(fnInfo.getUrlImage());
//
//        }
//        return array;
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            android.content.Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }


    public void addFavorite(String title, String content, String name, String urlImage) {

        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("title", title);
        taskMap.put("Content", content);
        taskMap.put("source", name);
        taskMap.put("UrlImage", urlImage);
        myRef2.push().setValue(taskMap);


        Log.i("patima", "addFavorite: PUSS");


    }

}


