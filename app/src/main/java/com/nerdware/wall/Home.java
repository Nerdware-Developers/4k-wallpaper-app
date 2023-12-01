package com.nerdware.wall;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    AdView mAdView, ban;
    private ProgressBar progressBar;
    private ArrayList<String> imageList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.ban3);
        ban = findViewById(R.id.ban2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        ban.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        imageList = new ArrayList<>();
        adapter = new MyAdapter (imageList, this);
        recyclerView.setAdapter(adapter);

        // Retrieve the category name from intent extras
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        // Retrieve images for the specified category
        retrieveImagesFromCategory(categoryName);
    }



    @SuppressLint("NotifyDataSetChanged")
    private void retrieveImagesFromCategory(String categoryName) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(categoryName);

        storageRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Add the image URL to the imageList
                            imageList.add(uri.toString());
                            // Notify the adapter that the data has changed
                            adapter.notifyDataSetChanged();
                        });
                    }
                    // Hide the progress bar after retrieving the images
                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occur during image retrieval
                    Toast.makeText(Home.this, "Failed to retrieve images: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        cats.loadads(Home.this);
    }

}