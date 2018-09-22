package com.example.nikos.unipiapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsDisplayActivity extends AppCompatActivity {


    TextView titleView;
    TextView descriptionView;
    TextView nameView;
    ImageView imageView;
    Context context;

    String title;
    String content;
    String name;
    String urlImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        //Get Content from Bundle

        //--------- Start Cases ---------//

        //First Case
//        title = this.getIntent().getExtras().getString("TITLE");
//        content = this.getIntent().getExtras().getString("CONTENT");
//        name = this.getIntent().getExtras().getString("NAME");
//        urlImage = this.getIntent().getExtras().getString("URLIMAGE");

        //Second Case
        List<Article> article = this.getIntent().getExtras().getParcelableArrayList("Articles");
        int position = this.getIntent().getExtras().getInt("Position");

        title = article.get(position).getTitle();
        content = article.get(position).getContent();
//        name = article.get(position).getSource().getName();
        urlImage = article.get(position).getUrlToImage();

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


    }
}
