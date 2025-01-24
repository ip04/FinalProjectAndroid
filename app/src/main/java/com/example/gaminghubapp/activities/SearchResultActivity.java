package com.example.gaminghubapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.example.gaminghubapp.R;
import com.example.gaminghubapp.domain.Datum;
import com.google.gson.Gson;

public class SearchResultActivity extends AppCompatActivity {

    private ImageView posterIV;
    private TextView titleTV, imdbRatingTV, yearTV, countryTV;
    private ProgressBar progressBar;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        String json = getIntent().getStringExtra("datum");
        if (json != null) {
            Datum datum = new Gson().fromJson(json, Datum.class);
            displayData(datum);
        }
    }

    private void initView() {
        posterIV = findViewById(R.id.search_movie_picIV);
        titleTV = findViewById(R.id.search_movie_nameTV);
        imdbRatingTV = findViewById(R.id.search_movie_starsTV);
        yearTV = findViewById(R.id.search_movie_yearTV);
        countryTV = findViewById(R.id.search_movie_countryTV);
        progressBar = findViewById(R.id.search_pogressBar);
        scrollView = findViewById(R.id.search_scrollView);
//        genresTV = findViewById(R.id.genreView);
    }


    private void displayData(Datum datum) {
        // הצגת נתונים ב-UI
//        progressBar.setVisibility(View.VISIBLE);
//        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        titleTV.setText(datum.getTitle());
        imdbRatingTV.setText("IMDB Rating: " + datum.getImdbRating());
        yearTV.setText("Year: " + datum.getYear());
        countryTV.setText("Country: " + datum.getCountry());
//        genresTV.setText("Genres: " + String.join(", ", datum.getGenres()));

        // הצגת תמונת הפוסטר
        Glide.with(this).load(datum.getPoster()).into(posterIV);
    }
}