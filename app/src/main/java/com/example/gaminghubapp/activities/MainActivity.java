package com.example.gaminghubapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gaminghubapp.R;
import com.example.gaminghubapp.adapters.CategoryListAdapter;
import com.example.gaminghubapp.adapters.GameListAdapter;
import com.example.gaminghubapp.adapters.SliderAdapters;
import com.example.gaminghubapp.domain.Datum;
import com.example.gaminghubapp.domain.GenresItem;
import com.example.gaminghubapp.domain.ListGame;
import com.example.gaminghubapp.domain.SearchItemDetail;
import com.example.gaminghubapp.domain.SliderItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText searchBoxET;
    private RecyclerView.Adapter adapterBestGames, adapterUpComing, adapterCategory;
    private RecyclerView recyclerViewBestGames, recyclerViewUpComing, recyclerViewCategory;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2, mStringRequest3;
    private ProgressBar loading1, loading2, loading3;
    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        banners();
        sendRequestBestGames();
        sendRequestUpComing();
        sendRequestCategory();
        setupSearchBox();
    }


    private void setupSearchBox() {
        searchBoxET.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBoxET.getText().toString().trim();
            if (!query.isEmpty()) {
                searchMovies(query);
            }
            return true;
        });
    }

    private void searchMovies(String query) {
        String url = "https://moviesapi.ir/api/v1/movies?q=" + query;
        mRequestQueue = Volley.newRequestQueue(this);

        StringRequest searchRequest = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new Gson();
            SearchItemDetail searchResults = gson.fromJson(response, SearchItemDetail.class);
            if (searchResults.getData() != null && !searchResults.getData().isEmpty()) {
                // Take the first result
                Datum datum = searchResults.getData().get(0); // נניח שאתה משתמש בתוצאה הראשונה
                String json = new Gson().toJson(datum);

                Intent intent = new Intent(this, SearchResultActivity.class);
                intent.putExtra("datum", json);
                startActivity(intent);
            } else {
                Log.i("TAG", "No results found for query: " + query);
                Toast.makeText(this, "No results found for query: " + query, Toast.LENGTH_SHORT).show();
            }
        }, error -> Log.i("TAG", "onErrorResponse: " + error.toString()));

        mRequestQueue.add(searchRequest);
    }

    private void sendRequestBestGames() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", response -> {
            Gson gson = new Gson();
            loading1.setVisibility(View.GONE);
            ListGame items = gson.fromJson(response, ListGame.class);
            adapterBestGames = new GameListAdapter(items);
            recyclerViewBestGames.setAdapter(adapterBestGames);
        }, error -> {
                loading1.setVisibility(View.GONE);
                Log.i("TAG", "onErrorResponse: "+error.toString());
            });
        mRequestQueue.add(mStringRequest);
    }

    private void sendRequestUpComing() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading3.setVisibility(View.VISIBLE);
        mStringRequest3 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2", response -> {
            Gson gson = new Gson();
            loading3.setVisibility(View.GONE);
            ListGame items = gson.fromJson(response, ListGame.class);
            adapterUpComing = new GameListAdapter(items);
            recyclerViewUpComing.setAdapter(adapterUpComing);
        }, error -> {
                loading3.setVisibility(View.GONE);
                Log.i("TAG", "onErrorResponse: "+error.toString());
            });
        mRequestQueue.add(mStringRequest3);
    }

    private void sendRequestCategory() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres", response -> {
            Gson gson = new Gson();
            loading2.setVisibility(View.GONE);
            ArrayList<GenresItem> catList = gson.fromJson(response, new TypeToken<ArrayList<GenresItem>>(){}.getType());
            adapterCategory = new CategoryListAdapter(catList);
            recyclerViewCategory.setAdapter(adapterCategory);
        }, error -> {
            loading2.setVisibility(View.GONE);
            Log.i("TAG", "onErrorResponse: "+error.toString());
        });
        mRequestQueue.add(mStringRequest2);
    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide4));
        sliderItems.add(new SliderItems(R.drawable.wide5));
        sliderItems.add(new SliderItems(R.drawable.wide6));
        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
            }
        });
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    protected void onPause(){
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onPostResume(){
        super.onPostResume();
    }

    @Override
    protected void onResume(){
        super.onResume();
        slideHandler.postDelayed(slideRunnable,2000);
    }

    private void initView() {
        searchBoxET = findViewById(R.id.et_searchBox);
        viewPager2 = findViewById(R.id.viewpagerSlider);
        recyclerViewBestGames = findViewById(R.id.view1);
        recyclerViewBestGames.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewUpComing = findViewById(R.id.view3);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCategory = findViewById(R.id.view2);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        loading1 = findViewById(R.id.progressBar1);
        loading2 = findViewById(R.id.progressBar2);
        loading3 = findViewById(R.id.progressBar3);
    }
}