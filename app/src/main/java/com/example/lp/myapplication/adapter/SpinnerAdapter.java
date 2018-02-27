package com.example.lp.myapplication.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lp.myapplication.R;
import com.example.lp.myapplication.models.Matiere;
import com.example.lp.myapplication.models.Note;

import java.util.List;

/**
 * Created by lp on 25/01/2018.
 */

public class SpinnerAdapter  extends ArrayAdapter<Matiere> {

    private int resource;

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Matiere> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Matiere item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name_matiere);
        name.setText(String.valueOf(item.getName()));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        Matiere item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name_matiere);
        name.setText(String.valueOf(item.getName()));

        return convertView;
    }

}
