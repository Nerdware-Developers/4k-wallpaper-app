package com.nerdware.wall;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Full_screen extends AppCompatActivity {


    //Declaring Variables
    ImageView imageView;
    Button downloadButton;
    Button setWallpaperButton;
    String imageUrl;
    AdView full;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen);

        activity = this;
        MobileAds.initialize(this);
        full = findViewById(R.id.full);
        AdRequest adRequest = new AdRequest.Builder().build();
        full.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        imageView = findViewById(R.id.full_img);
        downloadButton = findViewById(R.id.downloadButton);
        setWallpaperButton = findViewById(R.id.button);

        imageUrl = getIntent().getStringExtra("image");
        Glide.with(this).load(imageUrl).into(imageView);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImage();
            }
        });

        setWallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWallpaper();
            }
        });
    }

    private void downloadImage() {
        if (isWritePermissionGranted()) {
            Uri uri = Uri.parse(imageUrl);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            // Set the destination path for the downloaded file
            String fileName = "wallpaper_" + System.currentTimeMillis() + ".jpg";
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                downloadManager.enqueue(request);
                Toast.makeText(Full_screen.this, "Image download successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Full_screen.this, "Unable to start download", Toast.LENGTH_SHORT).show();
            }
        } else {
            requestWritePermission();
        }
    }

    private boolean isWritePermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestWritePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private void setWallpaper() {
        Glide.with(this).asBitmap().load(imageUrl).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(Full_screen.this);
                try {
                    wallpaperManager.setStream(getImageInputStream(resource));
                    Toast.makeText(Full_screen.this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Full_screen.this, "Failed to set wallpaper", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLoadCleared(Drawable placeholder) {
            }
        });
    }

    private InputStream getImageInputStream(Bitmap bitmap) {
        // Convert the bitmap to an InputStream
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return new ByteArrayInputStream(byteArray);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadImage();
            } else {
                Toast.makeText(this, "Write permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
