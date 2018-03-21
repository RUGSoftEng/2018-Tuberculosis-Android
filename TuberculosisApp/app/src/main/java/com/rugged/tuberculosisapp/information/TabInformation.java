package com.rugged.tuberculosisapp.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabInformation extends Fragment {

    public static final String TITLE = "TabInformation";
    public static final String TITLE_MESSAGE = "com.rugged.tuberculosisapp.TITLE";
    public static final String VIDEO_URLS_MESSAGE = "com.rugged.tuberculosisapp.URLS";
    private ListView listView;
    private List<Category> listCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab_information, container, false);

        // Get list view
        listView = view.findViewById(R.id.categoryList);

        // Prepare categories
        prepareListData();

        CategoryListAdapter adapter = new CategoryListAdapter(this.getContext(), R.layout.category_title, listCategories);

        // Set list adapter
        listView.setAdapter(adapter);

        // Add onItemClickListener that starts the video grid activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category itemClicked = (Category) parent.getItemAtPosition(position);

                Intent intent = new Intent(TabInformation.this.getActivity(), VideoGridActivity.class);
                intent.putExtra(TITLE_MESSAGE, itemClicked.getTitle());
                intent.putStringArrayListExtra(VIDEO_URLS_MESSAGE, itemClicked.getVideoUrls());
                startActivity(intent);
            }
        });

        return view;
    }

    /*
        Prepare the list data
     */
    private void prepareListData() {
        listCategories = new ArrayList<>();

        // Add data headers to children in list view
        // TODO: API call to get categories
        ArrayList<String> videoUrls1 = new ArrayList<>(), videoUrls2 = new ArrayList<>();
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");

        videoUrls2.add("anotherVideoUrl");
        videoUrls2.add("anotherVideoUrl");
        videoUrls2.add("anotherVideoUrl");
        listCategories.add(new Category("Category 1", videoUrls1));
        listCategories.add(new Category("Category 2", videoUrls2));
    }
}
