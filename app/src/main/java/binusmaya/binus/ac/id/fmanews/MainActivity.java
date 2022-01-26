package binusmaya.binus.ac.id.fmanews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.List;

import binusmaya.binus.ac.id.fmanews.Models.NewsList;
import binusmaya.binus.ac.id.fmanews.Models.Article;

import com.google.android.material.navigation.NavigationView;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ProgressDialog dialog;
    Button b1,b2,b3,b4,b5,b6,b7, btnLogin;
    SearchView searchView;

    //Side Navigation Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        // Initialize the HUAWEI Ads SDK.
        HwAds.init(this);

        // Obtain BannerView.
        BannerView bannerView = findViewById(R.id.hw_banner_view);
        // Set the ad unit ID and ad dimensions. "testw6vs28auh3" is a dedicated test ad unit ID.
        bannerView.setAdId("testw6vs28auh3");
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        // Set the refresh interval to 60 seconds.
        bannerView.setBannerRefresh(60);
        // Create an ad request to load an ad.
        AdParam adParam = new AdParam.Builder().build();
        bannerView.loadAd(adParam);

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Searching news of " + query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle("Search news");
        dialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    private final OnFetchDataListener<NewsList> listener = new OnFetchDataListener<NewsList>() {
        @Override
        public void onFetchData(List<Article> list, String message) {
            if (list.isEmpty()){
                Toast.makeText(MainActivity.this,"News no found", Toast.LENGTH_SHORT).show();
            }else{
                showNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An Error Occured!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<Article> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        NewsAdapter adapter = new NewsAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getText().toString();
        dialog.setTitle("Searching news of " + category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, category, null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent;
        RequestManager manager = new RequestManager(this);;

        switch (item.getItemId()){
            case R.id.nav_home:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_profile:
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_business:
                dialog.setTitle("Searching the business news");
                dialog.show();
                manager.getNewsHeadlines(listener, "business", null);
                break;
            case R.id.nav_entertainment:
                dialog.setTitle("Searching the entertainment news");
                dialog.show();
                manager.getNewsHeadlines(listener, "entertainment", null);
                break;
            case R.id.nav_general:
                dialog.setTitle("Searching the general news");
                dialog.show();
                manager.getNewsHeadlines(listener, "general", null);
                break;
            case R.id.nav_health:
                dialog.setTitle("Searching the health news");
                dialog.show();
                manager.getNewsHeadlines(listener, "health", null);
                break;
            case R.id.nav_science:
                dialog.setTitle("Searching the science news");
                dialog.show();
                manager.getNewsHeadlines(listener, "science", null);
                break;
            case R.id.nav_sports:
                dialog.setTitle("Searching the sport news");
                dialog.show();
                manager.getNewsHeadlines(listener, "sports", null);
                break;
            case R.id.nav_technology:
                dialog.setTitle("Searching the technology news");
                dialog.show();
                manager.getNewsHeadlines(listener, "technology", null);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}