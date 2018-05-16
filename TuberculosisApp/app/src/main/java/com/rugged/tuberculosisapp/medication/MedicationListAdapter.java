package com.rugged.tuberculosisapp.medication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.settings.LanguageHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MedicationListAdapter extends ArrayAdapter<Medication> implements CompoundButton.OnCheckedChangeListener {

    private Context mContext;
    private int mResourceId;
    private final List<Medication> mMedication;
    private final List<Integer> days;
    private Date date;
    public SparseBooleanArray mCheckedStates;

    // User has x days to change the taken state of medicines before it is locked
    private static final int DAYS_UNTIL_CHECKBOX_IS_LOCKED = 1;

    public MedicationListAdapter(Context context, int resourceId, List<Medication> medication, List<Integer> days) {
        super(context, resourceId, medication);
        this.mContext = context;
        this.mResourceId = resourceId;
        this.mMedication = medication;
        this.days = days;
    }

    public MedicationListAdapter(Context context, int resourceId, List<Medication> medication, Date date) {
        super(context, resourceId, medication);
        this.mContext = context;
        this.mResourceId = resourceId;
        this.mMedication = medication;
        this.date = date;
        this.mCheckedStates = new SparseBooleanArray();
        this.days = new ArrayList<>();
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

        if (medication != null) {
            // If this adapter is used for the dialog color the text
            if (mResourceId == R.layout.medication_row_dialog) {
                Date today = new Date();
                final CheckBox takenCheckBox = convertView.findViewById(R.id.takenCheckBox);

                TextView medicationTime = convertView.findViewById(R.id.medicationTime);
                TextView medicationDose = convertView.findViewById(R.id.medicationDose);

                // Check if date is before today or today, if so color medication according to their isTaken state
                if (date.before(today) || date.equals(today)) {
                    boolean isTaken = medication.isTaken();
                    if (isTaken) {
                        medicationName.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_dark));
                    } else {
                        medicationName.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
                    }

                    // Attach listener, update checkbox state
                    takenCheckBox.setOnCheckedChangeListener(this);
                    takenCheckBox.setTag(position);
                    takenCheckBox.setChecked(isTaken);
                }

                // Lock checkbox after a certain amount of days or if date is in the future
                if (checkBoxesAreLocked(date)) {
                    takenCheckBox.setEnabled(false);
                }
                DateFormat df = new SimpleDateFormat("HH:mm", new Locale(LanguageHelper.getCurrentLocale()));
                medicationTime.setText(df.format(medication.getTime()));
                medicationDose.setText(convertView.getResources().getQuantityString(R.plurals.medication_dose, medication.getDose(), medication.getDose()));
            } else {
                int day = days.get(position);

                Date today = new Date();
                ImageView takenImage = convertView.findViewById(R.id.takenImage);

                if(today.getDay() == day) {
                    if(medication.isTaken()) {
                        takenImage.setImageResource(R.drawable.ic_check) ;
                        takenImage.setTag("R.drawable.ic_check");
                    }

                } else {
                    takenImage.setImageResource(R.drawable.ic_line);
                    takenImage.setTag("R.drawable.ic_line");
                }
            }
            if(medication.getName().equals("Highly experimental pills")) {
                medicationName.setText("Experimental pills");
            } else {
                medicationName.setText(medication.getName());

            }

        }

        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCheckedStates.put((Integer)compoundButton.getTag(), b);
    }

    public boolean isChecked(Medication medication) {
        return mCheckedStates.get(getPosition(medication));
    }

    public Boolean checkBoxesAreLocked(Date date) {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -(DAYS_UNTIL_CHECKBOX_IS_LOCKED + 1));
        Date lastLegalTakenDay = cal.getTime();
        return date.before(lastLegalTakenDay) || date.after(today);
    }
}
