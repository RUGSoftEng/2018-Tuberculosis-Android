package com.rugged.tuberculosisapp.calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.medication.Medication;
import com.rugged.tuberculosisapp.medication.MedicationListAdapter;
import com.rugged.tuberculosisapp.settings.LanguageHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ViewDayFragment extends DialogFragment {

    private Date date;
    private ArrayList<Medication> medicationList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_day, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView titleMedicationDialog = view.findViewById(R.id.titleMedicationDialog);
        ListView medicationListView = view.findViewById(R.id.medicationListDialog);
        Button dismissButton = view.findViewById(R.id.buttonDismiss);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", new Locale(LanguageHelper.getCurrentLocale()));
        String dateFormatted = sdf.format(date);
        titleMedicationDialog.setText(getResources().getString(R.string.medication_date, dateFormatted));

        MedicationListAdapter adapter = new MedicationListAdapter(this.getActivity(), R.layout.medication_row_dialog, medicationList, date);
        medicationListView.setAdapter(adapter);

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMedicationList(ArrayList<Medication> medicationList) {
        this.medicationList = medicationList;
    }

}
