package com.rugged.tuberculosisapp.information;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;

public class CategoryListAdapter extends ArrayAdapter<String> {

    private final Context mContext;
    private final int mResourceId;
    private final List<String> titles;
    private final HashMap<String, Integer> mIdMap = new HashMap<>();

    CategoryListAdapter(Context context, int resourceId, List<String> titles) {
        super(context, resourceId, titles);
        this.mContext = context;
        this.mResourceId = resourceId;
        this.titles = titles;

        // Assign resourceId to titles
        for (int i = 0; i < titles.size(); i++) {
            mIdMap.put(titles.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        return mIdMap.get(getItem(position));
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
