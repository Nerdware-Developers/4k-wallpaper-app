package com.nerdware.wall;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class Discovery extends AppCompatActivity{

    //Declaring variables
    RecyclerView recyclerView;
    // Declare the InterstitialAd variable
    AdView mAdView, disc;
    List<categories> list;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        activity = this;
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.disc1);
        disc = findViewById(R.id.disc2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        disc.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);

        recyclerView = findViewById(R.id.disco);
        list = new ArrayList<>();

        // Set up RecyclerView with GridLayoutManager
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Populate the list with categories
        list.add(new categories("Cars", R.drawable.car));
        list.add(new categories("Bikes", R.drawable.bike));
        list.add(new categories("Girls", R.drawable.gal));
        list.add(new categories("Boys", R.drawable.boy));
        list.add(new categories("Animals", R.drawable.anim));
        list.add(new categories("Arts", R.drawable.art));
        list.add(new categories("Celebrity Crush", R.drawable.celeb));
        list.add(new categories("Creatives", R.drawable.create));
        list.add(new categories("DC Characters", R.drawable.dc));
        list.add(new categories("Movies", R.drawable.movie));
        list.add(new categories("Marvels", R.drawable.marvel));
        list.add(new categories("Games", R.drawable.game));
        list.add(new categories("Space", R.drawable.space));
        list.add(new categories("Army", R.drawable.army));
        list.add(new categories("Planes & jets", R.drawable.jets));
        list.add(new categories("Underwater", R.drawable.water));
        list.add(new categories("Technology", R.drawable.tech));
        list.add(new categories("Sunset & Sunrise", R.drawable.set));
        list.add(new categories("Mountain", R.drawable.mount));
        list.add(new categories("Graffiti", R.drawable.graf));
        list.add(new categories("Food & Drinks", R.drawable.food));
        list.add(new categories("Flowers", R.drawable.flow));
        list.add(new categories("Cities & places", R.drawable.city));
        list.add(new categories("Skeleton", R.drawable.skele));
        list.add(new categories("Dark Wallpapers", R.drawable.dark));
        list.add(new categories("Animations", R.drawable.anime));
        list.add(new categories("Quotes", R.drawable.quotes));
        list.add(new categories("Architecture", R.drawable.arch));
        list.add(new categories("Programming", R.drawable.code));
        list.add(new categories("Skies", R.drawable.sky));
        list.add(new categories("Sports", R.drawable.sport));
        list.add(new categories("Music", R.drawable.music));
        list.add(new categories("Christmas", R.drawable.xmas));
        list.add(new categories("Halloween", R.drawable.halo));

        /*list.add(new categories("Gangster", R.drawable.music));
        list.add(new categories("AI Generated", R.drawable.music));
        list.add(new categories("Historical", R.drawable.music));
        list.add(new categories("Babies", R.drawable.babies));
        list.add(new categories("Ships & Boats", R.drawable.music));
        */

        CatAdapter adapter = new CatAdapter(list, Discovery.this, activity);


        recyclerView.setAdapter(adapter);

        discos.loadads(Discovery.this);

    }
}