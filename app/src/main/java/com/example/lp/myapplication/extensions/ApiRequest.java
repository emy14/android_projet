package com.example.lp.myapplication.extensions;


import android.content.Context;
import android.util.Log;

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

    public void request(String link, final ApiRequestComplete<String> listener)
    {

        String url = urlApi + link;

        StringRequest jsObjRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

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
                        Log.d("Api request", error.getMessage());
                        listener.onError(error.getMessage().toString());


                    }
                });

        requestQueue.add(jsObjRequest);
    }
}


