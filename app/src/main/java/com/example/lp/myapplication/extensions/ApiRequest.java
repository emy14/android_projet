package com.example.lp.myapplication.extensions;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lp.myapplication.interfaces.ApiRequestComplete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by nme on 21/08/17.
 */

public class ApiRequest {


    private static ApiRequest instance = null;
    private static String urlApi ="http://192.168.240.14/api/api_rest/public/api/";


    public RequestQueue requestQueue;

    public ApiRequest(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized ApiRequest connect(Context context)
    {
        if (null == instance)
            instance = new ApiRequest(context);
        return instance;
    }

    public static synchronized ApiRequest getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(ApiRequest.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void request(final int method, String link, final ApiRequestComplete<String> listener)
    {

        String url = urlApi + link;

        final StringRequest req = new StringRequest
                (method, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Api request", "somePostRequest Response : " + response.toString());
                        if(null != response.toString())
                            try {
                                listener.onResponse(response.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(null != error){
                            listener.onError(error.toString());
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                return params;
            }
        };

        // requete send once, timeout 10 secondes
        req.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }



    public void requestPost(final int method, String link, JSONObject json, final ApiRequestComplete<JSONObject> listener)
    {

        String url = urlApi + link;
        JSONObject jsonObj = json;

        final JsonObjectRequest req = new JsonObjectRequest
                (method, url, json, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Api request", "somePostRequest Response : " + response.toString());
                        if(null != response.toString())
                            try {
                                listener.onResponse(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Api request", error.toString());
                        listener.onError(error.toString());


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                return params;
            }
        };

        // requete send once, timeout 10 secondes
        req.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }
}


