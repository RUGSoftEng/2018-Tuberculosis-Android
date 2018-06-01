package com.rugged.tuberculosisapp.reminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.rugged.tuberculosisapp.calendar.CalendarJSONHolder;
import com.rugged.tuberculosisapp.medication.Medication;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;
import com.rugged.tuberculosisapp.settings.LanguageHelper;
import com.rugged.tuberculosisapp.settings.UserData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReminderUpdateReceiver extends BroadcastReceiver {

    private ReminderSetter rs;

    @Override
    public void onReceive(Context context, Intent intent) {
        updateReminders(context);
    }

    private void updateReminders(Context context) {
        final Locale mLocale = new Locale(LanguageHelper.getCurrentLocale());
        final ArrayList<Medication> medicationList = new ArrayList<>();

        if (rs == null) {
            rs = new ReminderSetter(context);
        }

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", mLocale);
        String today = sdf.format(cal.getTime());

        final Call<List<CalendarJSONHolder>> call = serverAPI.getCalendarData(UserData.getIdentification().getId(),
                today, today, UserData.getIdentification().getToken());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<List<CalendarJSONHolder>> response = call.execute();

                    if (response.code() == 200) {
                        try {
                            for (CalendarJSONHolder jsonResponse : response.body()) {
                                Medication medication = jsonResponse.toMedication();
                                medicationList.add(medication);
                            }
                        } catch (Exception e) {
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
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rs.setReminders(new Date(), medicationList);
    }

}
