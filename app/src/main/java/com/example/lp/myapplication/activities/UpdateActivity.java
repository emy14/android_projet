package com.example.lp.myapplication.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.example.lp.myapplication.R;
import com.example.lp.myapplication.extensions.ApiRequest;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);

        mark =  (TextView) findViewById(R.id.note);
        quotient = (TextView) findViewById(R.id.quotient);
        coeff = (TextView) findViewById(R.id.coeff);
        matiere = (TextView) findViewById(R.id.matiere);
        ButtonValidation = (Button) findViewById(R.id.buttonValidation);
        ButtonDelete= (Button) findViewById(R.id.buttonDelete);
        error = (TextView) findViewById(R.id.error);

        Bundle bundle = this.getIntent().getExtras();

        mark.setText(bundle.getString("note"));
        quotient.setText(bundle.getString("quotient"));
        matiere.setText(bundle.getString("matiere"));
        coeff.setText(bundle.getString("coeff"));

    }
}
