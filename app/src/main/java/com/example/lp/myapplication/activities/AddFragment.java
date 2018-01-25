package com.example.lp.myapplication.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.lp.myapplication.R;
import com.example.lp.myapplication.extensions.ApiRequest;
import com.example.lp.myapplication.interfaces.ApiRequestComplete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lp on 24/01/2018.
 */

public class AddFragment extends Fragment {

    private TextView mark;
    private TextView quotient;
    private TextView coeff;
    private TextView matiere;
    private Button ButtonValidation;
    private TextView error;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_activity,
                container, false);

        mark =  rootView.findViewById(R.id.note);
        quotient = rootView.findViewById(R.id.quotient);
        coeff = rootView.findViewById(R.id.coeff);
        matiere = rootView.findViewById(R.id.matiere);
        ButtonValidation = rootView.findViewById(R.id.buttonValidation);
        error = rootView.findViewById(R.id.error);

        ButtonValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = true;

                if(coeff.getText().toString().trim().length() == 0){
                    error.setVisibility(View.VISIBLE);
                    error.setText("Merci de saisir un coefficient :)");
                    valid = false;
                }
                if(quotient.getText().toString().trim().length() == 0){
                    error.setVisibility(View.VISIBLE);
                    error.setText("Merci de saisir un quotient :)");
                    valid = false;
                }
                if(mark.getText().toString().trim().length() == 0){
                    error.setVisibility(View.VISIBLE);
                    error.setText("Merci de saisir une note :)");
                    valid = false;
                }
                if(matiere.getText().toString().trim().length() == 0){
                    error.setVisibility(View.VISIBLE);
                    error.setText("Merci de saisir une matière :)");
                    valid = false;
                }

                if(valid){
                    JSONObject note = new JSONObject();
                    try {
                        note.put("note", mark.getText().toString());
                        note.put("coeff", coeff.getText().toString());
                        note.put("matiere", matiere.getText().toString());
                        note.put("quotient", quotient.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    postNote(note);
                }
            }
        });

        return rootView;
    }

    private void postNote(JSONObject note) {
        ApiRequest.getInstance().requestPost(Request.Method.POST,  "add/1", note, new ApiRequestComplete<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) throws JSONException {
                if(result.getBoolean("succeed") == true){
                    ShowFragment newFragment = new ShowFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.containerLi, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }


}
