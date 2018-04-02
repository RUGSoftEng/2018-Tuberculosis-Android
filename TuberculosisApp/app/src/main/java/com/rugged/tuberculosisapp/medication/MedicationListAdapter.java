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

    private Context mContext;
    private int mResourceId;
    private final List<Medication> mMedication;


    public MedicationListAdapter(Context context, int resourceId, List<Medication> medication) {
        super(context, resourceId, medication);
        this.mContext = context;
        this.mResourceId = resourceId;
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
            convertView = LayoutInflater.from(getContext()).inflate(mResourceId, parent, false);
        }

        TextView medicationName = convertView.findViewById(R.id.medicationName);
        TextView medicationTime = convertView.findViewById(R.id.medicationTime);
        TextView medicationDose = convertView.findViewById(R.id.medicationDose);

        if (medication != null) {
            // If this adapter is used for the dialog color the text
            if (mResourceId == R.layout.medication_row_dialog) {
                boolean isTaken = medication.getTaken();
                if (isTaken) {
                    medicationName.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_dark));
                } else {
                    medicationName.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
                }
            }
            medicationName.setText(medication.getName());
            medicationTime.setText(medication.getTime());
            medicationDose.setText(convertView.getResources().getQuantityString(R.plurals.medication_dose, medication.getDose(), medication.getDose()));
        }

        return convertView;
    }

}
