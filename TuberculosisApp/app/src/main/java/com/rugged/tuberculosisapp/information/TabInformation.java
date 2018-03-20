package com.rugged.tuberculosisapp.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabInformation extends Fragment {

    public static final String TITLE = "TabInformation";
    ListView listView;
    List<String> listDataHeaders;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab_information, container, false);

        // Get list view
        listView = view.findViewById(R.id.categoryList);

        // Prepare categories
        prepareListData();

        CategoryListAdapter adapter = new CategoryListAdapter(this.getContext(), R.layout.category_title, listDataHeaders);

        // Set list adapter
        listView.setAdapter(adapter);

        return view;
    }

    /*
        Prepare the list data
     */
    private void prepareListData() {
        listDataHeaders = new ArrayList<>();

        // Add data headers to children in list view
        // TODO: API call to get categories
        listDataHeaders.add("Category 1");
        listDataHeaders.add("Category 2");
    }
}
