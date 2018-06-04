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
import com.rugged.tuberculosisapp.settings.LanguageHelper;
import com.rugged.tuberculosisapp.settings.UserData;
import com.rugged.tuberculosisapp.signin.Identification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private ArrayList<JSONVideoHolder> quizholder;

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
                Bundle bundle = new Bundle();
                bundle.putSerializable(VIDEO_URLS_MESSAGE, itemClicked.getVideoUrls());
                Intent intent = new Intent(TabInformation.this.getActivity(), VideoGridActivity.class);
                intent.putExtra(TITLE_MESSAGE, itemClicked.getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
        Prepare the list data
     */
    private void prepareListData() {
        listCategories = new ArrayList<>();
        final ArrayList<String> titles = new ArrayList<>();
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        // Here the method is the one you created in the ServerAPI interface
        final Call<ArrayList<String>> call = serverAPI.retrieveCategories(LanguageHelper.getCurrentLocale().toUpperCase());
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
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (final String title : titles) {
            final Call<List<JSONVideoHolder>> callVideo = serverAPI.retrieveVideoByCategory(title, LanguageHelper.getCurrentLocale().toUpperCase());

            Thread s = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Response<List<JSONVideoHolder>> response = callVideo.execute();
                        if (response.code() == 200) { // choose right code for successful API call (200 in this case)
                            if (response.body() != null) {
                                ArrayList<Urls> videos = new ArrayList<>();
                                ArrayList<Quiz> correspondingQuizzes;
                                for (JSONVideoHolder jsonResponse : response.body()){
                                    String temp = jsonResponse.getVideo().getReference();
                                    String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*";
                                    Pattern compiledPattern = Pattern.compile(pattern);
                                    Matcher matcher = compiledPattern.matcher(temp);
                                    if (matcher.find()) {
                                        temp = matcher.group();
                                        correspondingQuizzes = jsonResponse.getQuiz();
                                        videos.add(new Urls(temp,correspondingQuizzes));
                                    }
                                }
                                listCategories.add(new Category(title, videos));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
            s.start();

            try {
                s.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
