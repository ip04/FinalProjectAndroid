package com.example.gaminghubapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gaminghubapp.R;
import com.example.gaminghubapp.adapters.CategoryEachFilmListAdapter;
import com.example.gaminghubapp.domain.Datum;
import com.google.gson.Gson;

public class SearchResultActivity extends AppCompatActivity {

    private ImageView posterIV;
    private TextView titleTV, imdbRatingTV, yearTV, countryTV;
    private ImageView backBtn, favBtn;
    private ProgressBar progressBar;
    private NestedScrollView scrollView;
    private RecyclerView recyclerViewCategory;
    private RecyclerView.Adapter adapterCategory;


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
        backBtn.setOnClickListener(v -> finish());
    }

    private void initView() {
        posterIV = findViewById(R.id.search_movie_picIV);
        titleTV = findViewById(R.id.search_movie_nameTV);
        imdbRatingTV = findViewById(R.id.search_movie_starsTV);
        yearTV = findViewById(R.id.search_movie_yearTV);
        countryTV = findViewById(R.id.search_movie_countryTV);
        progressBar = findViewById(R.id.search_pogressBar);
        scrollView = findViewById(R.id.search_scrollView);
        backBtn = findViewById(R.id.backBtnIV);
        recyclerViewCategory = findViewById(R.id.search_genreView);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
//        genresTV = findViewById(R.id.search_genreView);
    }


    private void displayData(Datum datum) {
        // הצגת נתונים ב-UI
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        titleTV.setText(datum.getTitle());
        imdbRatingTV.setText("IMDB Rating: " + datum.getImdbRating());
        yearTV.setText("Year: " + datum.getYear());
        countryTV.setText("Country: " + datum.getCountry());
        if (datum.getGenres() != null){
            adapterCategory = new CategoryEachFilmListAdapter(datum.getGenres());
            recyclerViewCategory.setAdapter(adapterCategory);
        }
//        genresTV.setText("Genres: " + String.join(", ", datum.getGenres()));

        // הצגת תמונת הפוסטר
        Glide.with(this).load(datum.getPoster()).into(posterIV);
    }
}