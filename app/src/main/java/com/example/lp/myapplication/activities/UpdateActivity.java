package com.example.lp.myapplication.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button ButtonDelete;
    private TextView error;
    private SpinnerAdapter adapter;
    private ArrayList<Matiere> items;
    private Spinner matiereSpinner;
    private String id;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);

        mark =  (TextView) findViewById(R.id.note);
        quotient = (TextView) findViewById(R.id.quotient);
        coeff = (TextView) findViewById(R.id.coeff);
        matiere = (TextView) findViewById(R.id.mat);
        matiereSpinner = (Spinner) findViewById(R.id.matiere);

        ButtonValidation = (Button) findViewById(R.id.buttonValidation);
        ButtonDelete= (Button) findViewById(R.id.buttonDelete);
        error = (TextView) findViewById(R.id.error);
        


        Bundle bundle = this.getIntent().getExtras();

        id = bundle.getString("id");

        Log.d("helo", id);
        
        mark.setText(bundle.getString("note"));
        quotient.setText(bundle.getString("quotient"));

        matiere.setText(bundle.getString("matiere"));
        coeff.setText(bundle.getString("coeff"));
        matiere.setVisibility(View.GONE);
        matiereSpinner.setVisibility(View.VISIBLE);

        items = new ArrayList<>();
        adapter = new com.example.lp.myapplication.adapter.SpinnerAdapter(getApplicationContext(), R.layout.spinner, items);
        position = bundle.getInt("id_matiere");

        postMatiere();

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



                if(valid){
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
