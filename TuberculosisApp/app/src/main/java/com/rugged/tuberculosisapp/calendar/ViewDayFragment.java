package com.rugged.tuberculosisapp.calendar;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
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
    private MedicationListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_day, container, false);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog);

        TextView titleMedicationDialog = view.findViewById(R.id.titleMedicationDialog);
        ListView medicationListView = view.findViewById(R.id.medicationListDialog);
        Button dismissButton = view.findViewById(R.id.buttonDismiss);
        Button saveButton = view.findViewById(R.id.buttonSave);

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
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNewMedicineStates();
                    dismiss();
                }
            });
        }

        return view;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMedicationList(ArrayList<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    private void saveNewMedicineStates() {
        // Set medicine taken states to respective checkbox states
        for (Medication medication : medicationList) {
            medication.setTaken(adapter.isChecked(medication));
            // TODO: Add API call to update taken state in database
        }
    }

}
