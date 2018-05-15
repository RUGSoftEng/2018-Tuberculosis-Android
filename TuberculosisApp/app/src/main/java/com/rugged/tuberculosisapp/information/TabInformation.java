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
       final ArrayList<String> titles = new ArrayList<>();
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        final Call<ArrayList<String>> call = serverAPI.retrieveCategories(UserData.getIdentification().getToken(),titles); // here the method is the one you created in the ServerAPI interface
         Thread t = new Thread(new Runnable() {

        @Override

        public void run() {


            try {
                Response<ArrayList<String>> response = call.execute();
                if (response.code() == 200) { // choose right code for successful API call (200 in this case)
                    if (response.body() != null) {
                        titles.addAll(response.body());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        });t.start();

        try {

            t.join();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
        for(String title: titles) {
            final ArrayList<String> videos = new ArrayList<>();
            final Call<ArrayList<String>> callVideo = serverAPI.retrieveVideoByCategory(title,UserData.getIdentification().getToken(),videos );
            Thread s = new Thread(new Runnable() {

                @Override

                public void run() {


                    try {
                        Response<ArrayList<String>> response = callVideo.execute();
                        if (response.code() == 200) { // choose right code for successful API call (200 in this case)
                            if (response.body() != null) {
                                listCategories.add(new Category("Category 1",response.body()));

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }

            });s.start();

            try {

                s.join();

            } catch (InterruptedException e) {

                e.printStackTrace();

            }
        }


        // Add data headers to children in list view
        // TODO: API call to get categories
      /**  ArrayList<String> videoUrls1 = new ArrayList<>(), videoUrls2 = new ArrayList<>();
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");
        videoUrls1.add("yR51KVF4OX0");

        videoUrls2.add("IGZLkRN76Dc");
        videoUrls2.add("yR51KVF4OX0");
        listCategories.add(new Category("Category 1", videoUrls1));
        listCategories.add(new Category("Category 2", videoUrls2)); **/
    }
}
