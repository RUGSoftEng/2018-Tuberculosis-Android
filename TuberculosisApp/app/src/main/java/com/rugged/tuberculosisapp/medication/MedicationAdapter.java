package com.rugged.tuberculosisapp.medication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;

import java.util.List;

/**
 * Created by drgdg on 21-3-2018.
 */




public class MedicationAdapter extends BaseAdapter {

    private LayoutInflater mInflator;
    private final List<String> names;
    private final List<String> times;
    private final List<String> amounts;


    public MedicationAdapter(Context c, List<String> names, List<String> times, List<String> amounts) {
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

        View v = mInflator.inflate(R.layout.medication_layout_detail, null);

        TextView medicationName = v.findViewById(R.id.medicationName);
        TextView timeAndAmount = v.findViewById(R.id.whatTime);

        String name = names.get(i);
        String time = times.get(i);
        String amount = amounts.get(i);

        medicationName.setText(name);
        timeAndAmount.setText(time + ", " + amount);

        return v;
    }
}
