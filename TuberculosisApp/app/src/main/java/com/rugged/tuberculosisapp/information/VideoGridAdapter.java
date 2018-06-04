package com.rugged.tuberculosisapp.information;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.rugged.tuberculosisapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoGridAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener {

    private final Context mContext;
    private final List<Urls> videoUrls;
    private Map<View, YouTubeThumbnailLoader> loaders;

    VideoGridAdapter(Context context, List<Urls> videoUrls) {
        this.mContext = context;
        this.videoUrls = videoUrls;
        this.loaders = new HashMap<>();
    }

    @Override
    public int getCount() {
        return videoUrls.size();
    }

    @Override
    public String getItem(int position) {
        return videoUrls.get(position).getUrl();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        YouTubeThumbnailView youTubeThumbnailView;

        // Get videoUrl of grid item
        final String videoUrl = getItem(position);

        if (convertView == null) {
            // Initialize loader
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.thumbnail_holder, parent, false);

            youTubeThumbnailView = convertView.findViewById(R.id.thumbnailView);
            youTubeThumbnailView.setTag(videoUrl);

            youTubeThumbnailView.initialize(TabInformation.DEVELOPER_KEY, this);
        } else {
            youTubeThumbnailView = convertView.findViewById(R.id.thumbnailView);
            YouTubeThumbnailLoader loader = loaders.get(youTubeThumbnailView);

            if (loader == null) {
                // Loader is busy initializing
                youTubeThumbnailView.setTag(videoUrl);
            } else {
                // The loader is already initialized
                // TODO: find better loading image/change to gif
                youTubeThumbnailView.setImageResource(R.drawable.gif_loading);
                loader.setVideo(videoUrl);
            }
        }

        // Set width to fit 2 items next to each other
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int spacing = width / 9;
        spacing += spacing/10;
        width = (width - spacing) / 2;
        // Thumbnail has a 16:9 aspect ratio
        convertView.getLayoutParams().width = width;
        convertView.getLayoutParams().height = (width / 16) * 9;
        convertView.requestLayout();

        return convertView;
    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
        loaders.put(youTubeThumbnailView, youTubeThumbnailLoader);
        youTubeThumbnailView.setImageResource(R.drawable.gif_loading);
        youTubeThumbnailLoader.setVideo((String) youTubeThumbnailView.getTag());
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
        //TODO: implement error handling
    }

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : loaders.values()) {
            loader.release();
        }
    }

}