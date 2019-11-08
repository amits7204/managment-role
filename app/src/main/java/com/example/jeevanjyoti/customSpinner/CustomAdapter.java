package com.example.jeevanjyoti.customSpinner;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.jeevanjyoti.R;

import java.util.ArrayList;

import static android.graphics.Color.WHITE;


public class CustomAdapter extends ArrayAdapter<CustomItems> {

    public CustomAdapter(@NonNull Context context, ArrayList<CustomItems> customList) {
        super(context, 0, customList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    public View customView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout, parent, false);
        }
        CustomItems items = getItem(position);
//        ImageView spinnerImage = convertView.findViewById(R.id.marital_status_image_view);
        TextView spinnerName = convertView.findViewById(R.id.marital_status_name);
        if (items != null) {
            Log.w("CustomAdapter", "Name Of State: ");
//            spinnerImage.setVisibility(View.VISIBLE);
//            spinnerImage.setImageResource(items.getSpinnerImage());
            spinnerName.setText(items.getSpinnerText());
        }else {
            spinnerName.setText(items != null ? items.getSpinnerText() : null);
        }
        return convertView;
    }
}
