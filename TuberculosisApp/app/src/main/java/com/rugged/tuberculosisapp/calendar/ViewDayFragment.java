package com.rugged.tuberculosisapp.calendar;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.medication.Medication;
import com.rugged.tuberculosisapp.medication.MedicationListAdapter;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;
import com.rugged.tuberculosisapp.settings.LanguageHelper;
import com.rugged.tuberculosisapp.settings.UserData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.view.Window.FEATURE_NO_TITLE;

public class ViewDayFragment extends DialogFragment {

    private CalendarView calendarView;
    private Date date;
    private ArrayList<Medication> medicationList;
    private MedicationListAdapter adapter;
    private ProgressBar spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_day, container, false);
        getDialog().requestWindowFeature(FEATURE_NO_TITLE);

        TextView titleMedicationDialog = view.findViewById(R.id.titleMedicationDialog);
        ListView medicationListView = view.findViewById(R.id.medicationListDialog);
        Button dismissButton = view.findViewById(R.id.buttonDismiss);
        final Button saveButton = view.findViewById(R.id.buttonSave);
        spinner = view.findViewById(R.id.progressBar);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", new Locale(LanguageHelper.getCurrentLocale()));
        String dateFormatted = sdf.format(date);
        titleMedicationDialog.setText(getResources().getString(R.string.medication_date, dateFormatted));

        adapter = new MedicationListAdapter(this.getActivity(), R.layout.medication_row_dialog, medicationList, date);
        medicationListView.setAdapter(adapter);

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Remove save button if checkboxes are locked
        if (adapter.checkBoxesAreLocked(date)) {
            ((ViewManager)saveButton.getParent()).removeView(saveButton);
        } else {
            // This onTouchListener is added to force the loading spinner to be displayed before
            // the saveNewMedicineStates method is called, invalidate doesn't work since UI is
            // handled using a different thread..
            saveButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    // Show loading spinner
                    spinner.setVisibility(View.VISIBLE);
                    return false;
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveNewMedicineStates();
                    dismiss();
                }
            });
        }

        return view;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMedicationList(ArrayList<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    private void saveNewMedicineStates() {
        // Set medicine taken states to respective checkbox states
        List<CalendarJSONHolder> newJsonData = new ArrayList<>();
        for (Medication medication : medicationList) {
            medication.setTaken(adapter.isChecked(medication));

            // Build objects to send in POST request body
            Date time = medication.getTime();
            String name = medication.getName();
            int dose = medication.getDose();
            Boolean isTaken = medication.getTaken();
            CalendarJSONHolder bodyObject = new CalendarJSONHolder(name, time, dose, isTaken, date);

            newJsonData.add(bodyObject);
        }

        // Update taken state API call
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        final Call<ResponseBody> call = serverAPI.updateTakenState(UserData.getIdentification().getId(),
                UserData.getIdentification().getToken(), newJsonData);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ResponseBody> response = call.execute();

                    Resources res = getResources();
                    if (response.code() == 200) {
                        showToast(res.getString(R.string.data_updated, res.getString(R.string.title_medication)), Toast.LENGTH_SHORT);
                    } else {
                        showToast(res.getString(R.string.save_error, res.getString(R.string.title_medication)), Toast.LENGTH_LONG);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        calendarView.updateCalendar();
    }

}
