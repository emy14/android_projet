package com.example.lp.myapplication.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.lp.myapplication.R;
import com.example.lp.myapplication.adapter.SpinnerAdapter;
import com.example.lp.myapplication.extensions.ApiRequest;
import com.example.lp.myapplication.interfaces.ApiRequestComplete;
import com.example.lp.myapplication.models.Matiere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lp on 25/01/2018.
 */

public class UpdateActivity extends AppCompatActivity {

    private TextView mark;
    private TextView quotient;
    private TextView coeff;
    private TextView matiere;
    private Button ButtonValidation;
    private Button ButtonUpdate;
    private Button ButtonDelete;
    private TextView error;
    private SpinnerAdapter adapter;
    private ArrayList<Matiere> items;
    private Spinner matiereSpinner;
    private String id;
    private Integer position;
    private LinearLayout updateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

            mark = (TextView) findViewById(R.id.note);
            quotient = (TextView) findViewById(R.id.quotient);
            coeff = (TextView) findViewById(R.id.coeff);
            matiereSpinner = (Spinner) findViewById(R.id.matiere);
            updateLayout = (LinearLayout) findViewById(R.id.update);
            ButtonValidation = (Button) findViewById(R.id.buttonValidation);
            ButtonUpdate = (Button) findViewById(R.id.buttonVal);
            ButtonDelete = (Button) findViewById(R.id.buttonDelete);
            error = (TextView) findViewById(R.id.error);

            ButtonValidation.setVisibility(View.GONE);
            updateLayout.setVisibility(View.VISIBLE);


            Bundle bundle = this.getIntent().getExtras();

            id = bundle.getString("id");


            mark.setText(bundle.getString("note"));
            quotient.setText(bundle.getString("quotient"));

            coeff.setText(bundle.getString("coeff"));

            items = new ArrayList<>();
            adapter = new com.example.lp.myapplication.adapter.SpinnerAdapter(getApplicationContext(), R.layout.spinner, items);
            position = bundle.getInt("id_matiere");

            postMatiere();

            ButtonUpdate.setOnClickListener(new View.OnClickListener() {
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


                    if (valid) {
                        JSONObject note = new JSONObject();
                        try {
                            note.put("note", mark.getText().toString());
                            note.put("coeff", coeff.getText().toString());
                            note.put("matiere", matiereSpinner.getSelectedItemPosition() + 1);
                            note.put("quotient", quotient.getText().toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        postNote(note);
                    }
                }
            });


            ButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete();
                }
            });


    }

    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences sharedPreferences =  getSharedPreferences("USER", Context.MODE_PRIVATE);
        if(!sharedPreferences.contains("email")){
            Fragment newFragment = new ConnexionFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.containerLi, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
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

                matiereSpinner.setAdapter(adapter);
                matiereSpinner.setSelection(position - 1);
            }

            @Override
            public void onError(String error) {

            }
        });
    }


    private void postNote(JSONObject note) {
        ApiRequest.getInstance().requestPost(Request.Method.POST,  "update/"  + id, note, new ApiRequestComplete<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) throws JSONException {
                if(result.getBoolean("succeed")){
                    Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void delete() {
        ApiRequest.getInstance().requestPost(Request.Method.POST,  "delete/"  + id, null, new ApiRequestComplete<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) throws JSONException {
                if(result.getBoolean("succeed")){
                    Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}
