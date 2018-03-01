package com.example.lp.myapplication.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.lp.myapplication.R;
import com.example.lp.myapplication.extensions.ApiRequest;
import com.example.lp.myapplication.interfaces.ApiRequestComplete;
import com.example.lp.myapplication.models.Matiere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lp on 24/01/2018.
 */

public class AddFragment extends Fragment {

    private TextView mark;
    private TextView quotient;
    private TextView coeff;
    private Spinner matiere;
    private Button ButtonValidation;
    private TextView error;
    private LinearLayout updateLayout;

    private ArrayList<Matiere> items;
    private SpinnerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_activity,
                container, false);

            mark = rootView.findViewById(R.id.note);
            quotient = rootView.findViewById(R.id.quotient);
            coeff = rootView.findViewById(R.id.coeff);
            matiere = rootView.findViewById(R.id.matiere);
            ButtonValidation = rootView.findViewById(R.id.buttonValidation);
            error = rootView.findViewById(R.id.error);
            updateLayout = rootView.findViewById(R.id.update);
            items = new ArrayList<>();
            adapter = new com.example.lp.myapplication.adapter.SpinnerAdapter(getActivity(), R.layout.spinner, items);

            updateLayout.setVisibility(View.GONE);
            postMatiere();
            ButtonValidation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean valid = true;

                    if (coeff.getText().toString().trim().length() == 0) {
                        error.setVisibility(View.VISIBLE);
                        error.setText("Merci de saisir un coefficient :)");
                        valid = false;
                    }
                    if (quotient.getText().toString().trim().length() == 0) {
                        error.setVisibility(View.VISIBLE);
                        error.setText("Merci de saisir un quotient :)");
                        valid = false;
                    }
                    if (mark.getText().toString().trim().length() == 0) {
                        error.setVisibility(View.VISIBLE);
                        error.setText("Merci de saisir une note :)");
                        valid = false;
                    }
                    if (matiere.getSelectedItem().toString().trim().length() == 0) {
                        error.setVisibility(View.VISIBLE);
                        error.setText("Merci de selectionner une matière :)");
                        valid = false;
                    }


                    if (valid) {
                        JSONObject note = new JSONObject();
                        try {
                            note.put("note", mark.getText().toString());
                            note.put("coeff", coeff.getText().toString());
                            note.put("matiere", matiere.getSelectedItemPosition() + 1);
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

    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);

        if(!sharedPreferences.contains("email")){
            Fragment newFragment = new ConnexionFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.containerLi, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void postNote(JSONObject note) {
        ApiRequest.getInstance().requestPost(Request.Method.POST,  "add/1", note, new ApiRequestComplete<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) throws JSONException {
                if(result.getBoolean("succeed")){
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

    private void postMatiere() {
        ApiRequest.getInstance().request(Request.Method.GET,  "matieres", new ApiRequestComplete<String>() {
            @Override
            public void onResponse(String result) throws JSONException {
                JSONArray matieres = new JSONArray(result);

                for(int numberMatieres = 0 ; numberMatieres < matieres.length() ; numberMatieres ++){
                    JSONObject matiere = matieres.getJSONObject(numberMatieres);
                    Matiere m = new Matiere(matiere.getInt("id"), matiere.getString("nom_matiere"));
                    items.add(m);
                }

                matiere.setAdapter(adapter);


            }

            @Override
            public void onError(String error) {

            }
        });
    }


}
