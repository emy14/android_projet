package com.example.lp.myapplication.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lp.myapplication.R;
import com.example.lp.myapplication.models.Notes;

import java.util.List;

/**
 * Created by lp on 25/01/2018.
 */

public class NotesAdapter extends ArrayAdapter<Notes> {

    private int resource;

    public NotesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Notes> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Notes item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView note = convertView.findViewById(R.id.notetx);
        TextView matiere = convertView.findViewById(R.id.matieretx);
        TextView quotient = convertView.findViewById(R.id.quotienttx);
        TextView coeff = convertView.findViewById(R.id.coefftx);

        note.setText(String.valueOf(item.getNotes()));
        matiere.setText(item.getMatiere());
        quotient.setText(String.valueOf(item.getQuotient()));
        coeff.setText("Coeff :" + String.valueOf(item.getCoeff()));

        return convertView;
    }

}
