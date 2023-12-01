package com.nerdware.wall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final ArrayList<String> list;
    private final Context context;
    Activity activity;
    int counter = 0;
    private static final int AD_DISPLAY_INTERVAL = 3;

    public MyAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = list.get(position);
        if (imageUrl != null) {
            Glide.with(context).load(imageUrl).into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.holder);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context != null) {
                    counter++;
                    if (counter % AD_DISPLAY_INTERVAL == 0) {
                        if (discos.interstitialAd != null) {
                            discos.interstitialAd.show(activity);
                            discos.interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    discos.interstitialAd = null;
                                    loadAdAndLaunchFullScreenActivity(imageUrl);
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                    discos.interstitialAd = null;
                                    loadAdAndLaunchFullScreenActivity(imageUrl);
                                }
                            });
                        } else {
                            loadAdAndLaunchFullScreenActivity(imageUrl);
                        }
                    } else {
                        launchFullScreenActivity(imageUrl);
                            }
                } else {
                    Toast.makeText(null, "Context is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadAdAndLaunchFullScreenActivity(String imageUrl) {
        if (context != null) {
            discos.loadads((Activity) context);
            launchFullScreenActivity(imageUrl);
        } else {
            Toast.makeText(null, "Context is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchFullScreenActivity(String imageUrl) {
        if (context != null) {
            Intent intent = new Intent(context, Full_screen.class);
            intent.putExtra("image", imageUrl);
            context.startActivity(intent);
        } else {
            Toast.makeText(null, "Context is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}
