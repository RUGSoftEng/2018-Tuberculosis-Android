package com.rugged.tuberculosisapp.medication;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.Random;

import com.rugged.tuberculosisapp.R;

public class ViewMedicationFragment extends DialogFragment {

    private String medicationName;
    private boolean hasTaken;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_medication, container, false);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog);

        TextView medicationNameView = view.findViewById(R.id.medicationName);
        ImageView hasTakenView = view.findViewById(R.id.hasTakenImage);
        medicationNameView.setText(medicationName);


        //Placeholder, should be API call
        Random rand = new Random();
        int  n = rand.nextInt(2);
        if(n < 1) {
            hasTaken = true;
        } else {
            hasTaken = false;
        }


        if(!hasTaken) {
            hasTakenView.setImageResource(R.drawable.ic_cross);
        }

        return view;
    }

    public void setMedicationName(String name) {
        medicationName = name;
    }



}
