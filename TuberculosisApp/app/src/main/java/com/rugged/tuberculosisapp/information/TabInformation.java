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
import android.widget.Toast;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;
import com.rugged.tuberculosisapp.settings.UserData;
import com.rugged.tuberculosisapp.signin.Identification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TabInformation extends Fragment {

    public static final String TITLE = "TabInformation";
    public static final String TITLE_MESSAGE = "com.rugged.tuberculosisapp.TITLE";
    public static final String VIDEO_URLS_MESSAGE = "com.rugged.tuberculosisapp.URLS";

    // YouTube Data API Developer key
    public static final String DEVELOPER_KEY = "AIzaSyB4QU6ZSKlGnXDN8Bnf2xq-l8MUSLCbvn4";

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
       ArrayList<String>  Tubercul = new ArrayList<>();
        ArrayList<String> Treatment  = new ArrayList<>();
        ArrayList<String>  Preve = new ArrayList<>();
        ArrayList<String>  Oth = new ArrayList<>();


        Tubercul.add("fXiXGRlvH70");
        Tubercul.add("_GCGhSnmtyg");
        Tubercul.add("XJUGtouYizM");

        Treatment.add("y4YobMWMoyU");
        Treatment.add("29rigqhJgRk");
        Treatment.add("aq9Nuq2fDwY");

        Preve.add("n7blVDVDAaU");
        Preve.add("hP7R3f5YzVg");
        Preve.add("KTbgaQvIUwI");

        Oth.add("o3RxQZCNFk0");

        listCategories.add(new Category("Tuberculosis", Tubercul));
        listCategories.add(new Category("Treatment", Treatment));
        listCategories.add(new Category("Prevention", Preve));
        listCategories.add(new Category("Other", Oth));

    }
}
