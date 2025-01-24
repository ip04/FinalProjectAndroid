package com.example.gaminghubapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.gaminghubapp.R;
import com.example.gaminghubapp.adapters.ActorListAdapter;
import com.example.gaminghubapp.adapters.CategoryEachFilmListAdapter;
import com.example.gaminghubapp.domain.Datum;
import com.example.gaminghubapp.domain.FilmItem;
import com.example.gaminghubapp.domain.SearchItemDetail;
import com.google.gson.Gson;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTV, movieRateTV, movieTimeTV, movieActorsInfoTV, movieSummaryTV;
    private ImageView backBtnIV, favBtnIV, detailPictureIV;
    private RecyclerView recyclerViewActors, recyclerViewCategory;
    private RecyclerView.Adapter adapterActorList, adapterCategory;
    private NestedScrollView scrollView;
    private int idFilm;
    private String filmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idFilm = getIntent().getIntExtra("id", 0);
        filmName = getIntent().getStringExtra("searchResults");
        initView();
        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idFilm, response -> {
            Gson gson = new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

            FilmItem item = gson.fromJson(response, FilmItem.class);
            Glide.with(DetailActivity.this)
                    .load(item.getPoster())
                    .into(detailPictureIV);

            titleTV.setText(item.getTitle());
            movieRateTV.setText(item.getImdbRating());
            movieTimeTV.setText(item.getRuntime());
            movieSummaryTV.setText(item.getPlot());
            movieActorsInfoTV.setText(item.getActors());

            if (item.getImages() != null){
                adapterActorList = new ActorListAdapter(item.getImages());
                recyclerViewActors.setAdapter(adapterActorList);
            }
            if (item.getGenres() != null){
                adapterCategory = new CategoryEachFilmListAdapter(item.getGenres());
                recyclerViewCategory.setAdapter(adapterCategory);
            }
        }, error -> progressBar.setVisibility(View.GONE));
        mRequestQueue.add(mStringRequest);
    }




    private void initView() {
        progressBar = findViewById(R.id.detail_pogressBar);
        scrollView = findViewById(R.id.detail_scrollView);
        titleTV = findViewById(R.id.detail_movie_nameTV);
        movieRateTV = findViewById(R.id.detail_movie_starsTV);
        movieTimeTV = findViewById(R.id.detail_movie_timeTV);
        movieActorsInfoTV = findViewById(R.id.detail_movie_actorsTV);
        movieSummaryTV = findViewById(R.id.detail_movie_summaryTV);
        detailPictureIV = findViewById(R.id.detail_movie_picIV);
        backBtnIV = findViewById(R.id.backBtnIV);
        favBtnIV = findViewById(R.id.favBtnIV);
        recyclerViewCategory = findViewById(R.id.genreView);
        recyclerViewActors = findViewById(R.id.detail_imagesRecycler);
        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        backBtnIV.setOnClickListener(v -> finish());
    }
}