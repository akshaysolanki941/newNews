package com.example.akshaysolanki.newnews;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.akshaysolanki.newnews.Fragments.NewsIN;
import com.example.akshaysolanki.newnews.Fragments.NewsUS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "b9f477f95f334b33bc6abb474a228690";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    RecyclerView recyclerView;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setUpToolbar();

        getNews("general", API_KEY);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        getNews("general", API_KEY);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_entertainment:
                        getNews("entertainment", API_KEY);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_science:
                        getNews("science", API_KEY);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_sports:
                        getNews("sports", API_KEY);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_technology:
                        getNews("technology", API_KEY);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_health:
                        getNews("health", API_KEY);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_business:
                        getNews("business", API_KEY);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_setting:
                        //code
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_about:
                        //code
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });
    }

    private void setUpToolbar() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void getNews(String category, String api_key) {

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<FeedGeneral> call = apiInterface.getNews(category, api_key);
        call.enqueue(new Callback<FeedGeneral>() {
            @Override
            public void onResponse(Call<FeedGeneral> call, Response<FeedGeneral> response) {
                List<Article> articleList = response.body().getArticles();
                List<Article> extendedArticle = new ArrayList<>();
                extendedArticle.addAll(response.body().getArticles());
                Adapter adapter = new Adapter(MainActivity.this, extendedArticle);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FeedGeneral> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void searchNews(String query, String api_key) {
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<FeedGeneral> call = apiInterface.searchNews(query, api_key);
        call.enqueue(new Callback<FeedGeneral>() {
            @Override
            public void onResponse(Call<FeedGeneral> call, Response<FeedGeneral> response) {
                List<Article> articleList = response.body().getArticles();
                List<Article> extendedArticle = new ArrayList<>();
                extendedArticle.addAll(response.body().getArticles());
                Adapter adapter = new Adapter(MainActivity.this, extendedArticle);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FeedGeneral> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchNews(query, API_KEY);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}



