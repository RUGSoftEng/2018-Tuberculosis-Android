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

public class CategoryListAdapter extends ArrayAdapter<Category> {

    private final Context mContext;
    private final int mResourceId;
    private final List<Category> mCategories;
    private final HashMap<Category, Integer> mIdMap = new HashMap<>();

    CategoryListAdapter(Context context, int resourceId, List<Category> categories) {
        super(context, resourceId, categories);
        this.mContext = context;
        this.mResourceId = resourceId;
        this.mCategories = categories;

        // Assign resourceId to titles
        int i = 0;
        for (Category category : categories) {
            mIdMap.put(category, i);
            i++;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Category category = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_title, parent, false);
        }

        TextView categoryTitle = (TextView) convertView.findViewById(R.id.categoryTitle);

        if (category != null) {
            categoryTitle.setText(category.getTitle());
        }

        return convertView;
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
