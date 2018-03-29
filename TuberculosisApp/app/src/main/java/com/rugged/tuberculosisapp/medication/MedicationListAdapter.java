package com.rugged.tuberculosisapp.medication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;

import java.util.List;

public class MedicationListAdapter extends ArrayAdapter<Medication> {

    private LayoutInflater mInflater;
    private final List<Medication> mMedication;


    MedicationListAdapter(Context context, int resourceId, List<Medication> medication) {
        super(context, resourceId, medication);
        this.mMedication = medication;
    }


    @Override
    public int getCount() {
        return mMedication.size();
    }

    @Override
    public Medication getItem(int i) {
        return mMedication.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Medication medication = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.medication_row, parent, false);
        }

        TextView medicationName = convertView.findViewById(R.id.medicationName);
        TextView medicationTime = convertView.findViewById(R.id.medicationTime);
        TextView medicationDose = convertView.findViewById(R.id.medicationDose);

        if (medication != null) {
            medicationName.setText(medication.getName());
            medicationTime.setText(medication.getTime());
            medicationDose.setText(convertView.getResources().getQuantityString(R.plurals.medication_dose, medication.getDose(), medication.getDose()));
        }

        return convertView;
    }

}
