package com.example.lp.myapplication.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;


import com.example.lp.myapplication.R;

/**
 * Created by lp on 24/01/2018.
 */


import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class ConnexionFragment extends Fragment {
    private LoginButton loginButton;
    private Button logoutButton;

    private CallbackManager callbackManager;
    private String email;
    private TextView connexionMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.connexion_activity,
                container, false);

         callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        logoutButton = (Button) rootView.findViewById(R.id.buttondeco);
        connexionMessage = (TextView) rootView.findViewById(R.id.co);

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                try {
                                    email = object.getString("email");
                            /*        String first_name = object.getString("first_name");
                                    String last_name = object.getString("last_name");*/
                                    String id = object.getString("id");

                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("email", email)
                                                            .putString("id", id).commit();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email");
                request.setParameters(parameters);
                request.executeAsync();

                loginButton.setVisibility(View.GONE);
                connexionMessage.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {


            }
        });


        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume(){
        super.onResume();

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
                if(sharedPreferences.contains("email")) {
                    loginButton.setVisibility(View.GONE);

                    logoutButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LoginManager.getInstance().logOut();
                            sharedPreferences.edit().clear().commit();


                            Fragment newFragment = new ConnexionFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.containerLi, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                }
                else{
                    logoutButton.setVisibility(View.GONE);
                }

        }





}
