package com.rugged.tuberculosisapp.information;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;

import java.util.HashMap;
import java.util.List;

public class VideoGridAdapter extends ArrayAdapter<String> {

    private final Context mContext;
    private final int mResourceId;
    private final List<String> videoUrls;

    VideoGridAdapter(Context context, int resourceId, List<String> videoUrls) {
        super(context, resourceId, videoUrls);
        this.mContext = context;
        this.mResourceId = resourceId;
        this.videoUrls = videoUrls;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // TODO: use videoUrl to retrieve thumbnail
        String videoUrl = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.thumbnail_holder, parent, false);
        }

        return convertView;
    }
}
