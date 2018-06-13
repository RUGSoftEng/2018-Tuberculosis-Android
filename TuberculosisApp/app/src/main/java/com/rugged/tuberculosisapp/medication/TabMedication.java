package com.rugged.tuberculosisapp.medication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ImageView;

import android.widget.ListView;

import android.widget.Toast;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.calendar.CalendarJSONHolder;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;
import com.rugged.tuberculosisapp.settings.LanguageHelper;
import com.rugged.tuberculosisapp.settings.UserData;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TabMedication extends Fragment {

    public static final String TITLE = "TabMedication";

    private ListView medicationListView;

    private List<Medication> medicationList;

    private Calendar currentDate = Calendar.getInstance();

    private Locale mLocale = new Locale(LanguageHelper.getCurrentLocale());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_medication, container, false);

        prepareListData();

        medicationListView = view.findViewById(R.id.medicationList);

        final MedicationListAdapter adapter = new MedicationListAdapter(this.getContext(), R.layout.medication_row, medicationList);

        medicationListView.setAdapter(adapter);

        medicationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView takenImage = view.findViewById(R.id.takenImage);
                if (takenImage.getTag().equals("R.drawable.ic_exclam")) {
                    showToast(getString(R.string.taken_text) + " " + getString(R.string.answer_no), Toast.LENGTH_LONG);
                } else if (takenImage.getTag().equals("R.drawable.ic_check")) {
                    showToast(getString(R.string.taken_text) + " " + getString(R.string.answer_yes), Toast.LENGTH_LONG);
                } else if (takenImage.getTag().equals("nothing")) {
                    showToast(getString(R.string.taken_text) + " " + getString(R.string.answer_not_needed), Toast.LENGTH_LONG);
                } else{
                    String difference = (String) takenImage.getTag();
                    showToast(difference, Toast.LENGTH_LONG);
                }
            }

            private void showToast(final String text, final int duration) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), text, duration).show();
                    }
                });
            }
        });

        return view;
    }

    private void prepareListData() {

        medicationList = new ArrayList<>();

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        Calendar cal = (Calendar) currentDate.clone();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", mLocale);
        String fromDate = sdf.format(cal.getTime());

        cal.add(Calendar.WEEK_OF_MONTH, 1);
        String toDate = sdf.format(cal.getTime());
        final Call<List<CalendarJSONHolder>> call = serverAPI.getCalendarData(UserData.getIdentification().getId(),
                fromDate, toDate, UserData.getIdentification().getToken());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<List<CalendarJSONHolder>> response = call.execute();

                    // Successful API call
                    // We assume here that a medicine is taken at least once a week
                    if (response.code() == 200) {
                        try {
                            for (CalendarJSONHolder jsonResponse : response.body()) {
                                Medication medication = jsonResponse.toMedication();
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", mLocale);
                                if(!medicationList.contains(medication)) {
                                    medication.setDay(format.parse(jsonResponse.getDate()).getDay());
                                    medicationList.add(medication);
                                }
                            }
                        } catch (Exception e) {
                            // TODO: advanced exception handling, catch specific exceptions: nullPointer, parse etc.
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        try {
            // Wait for the thread to finish
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Sort on time
        Collections.sort(this.medicationList, new Comparator<Medication>() {
            @Override
            public int compare(Medication o1, Medication o2) {
                return o1.getTimeIntervalEnd().compareTo(o2.getTimeIntervalEnd());
            }
        });

        //Sort on today

        Collections.sort(this.medicationList, new Comparator<Medication>() {
            @Override
            public int compare(Medication o1, Medication o2) {
                int today = new Date().getDay();
                int b1 = o1.getDay() == today ? 1 : 0;
                int b2 = o2.getDay() == today ? 1 : 0;

                return b2 - b1;
            }
        });

    }

}
