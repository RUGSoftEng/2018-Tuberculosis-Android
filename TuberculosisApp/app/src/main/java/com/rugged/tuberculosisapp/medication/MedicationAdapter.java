package com.rugged.tuberculosisapp.medication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;

import java.util.List;

public class MedicationAdapter extends BaseAdapter {

    private LayoutInflater mInflator;
    private final List<String> names;
    private final List<String> times;
    private final List<String> amounts;


    MedicationAdapter(Context c, List<String> names, List<String> times, List<String> amounts) {
        this.names = names;
        this.times = times;
        this.amounts = amounts;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = mInflator.inflate(R.layout.medication_layout_detail, viewGroup, false);
        }

        TextView medicationName = view.findViewById(R.id.medicationName);
        TextView timeAndAmount = view.findViewById(R.id.whatTime);

        Button setTime = view.findViewById(R.id.buttonswdwad);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button setTime = v.findViewById(R.id.buttonswdwad);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        String name = names.get(i);
        String time = times.get(i);
        String amount = amounts.get(i);

        medicationName.setText(name);
        timeAndAmount.setText(view.getResources().getString(R.string.time_amount, time, amount));

        return view;
    }
}
