package com.nerdware.wall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    private final List<categories> list;
    private final Activity activity;
    private AppOpenAd appOpenAd;
    private AppOpenAd.AppOpenAdLoadCallback appOpenAdLoadCallback;
    private static final boolean isAppOpenAdShowing = false;

    private int counter = 0;
    private static final int AD_DISPLAY_INTERVAL = 3;

    public CatAdapter(List<categories> list, Context context, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.catego, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.textView.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if (counter % AD_DISPLAY_INTERVAL == 0) {
                    if (cats.interstitialAd != null) {
                        cats.interstitialAd.show(activity);
                        cats.interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                cats.interstitialAd = null;
                                cats.loadads(activity);
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                cats.interstitialAd = null;
                                cats.loadads(activity);
                            }
                        });
                    }
                } else {
                    int position = holder.getAdapterPosition();
                    String categoryName = list.get(position).getName();

                    Intent intent = new Intent(view.getContext(), Home.class);
                    intent.putExtra("CATEGORY_NAME", categoryName);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cat);
            textView = itemView.findViewById(R.id.cat_img);
        }
    }
}
