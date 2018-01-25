package com.example.lp.myapplication.interfaces;

import org.json.JSONException;

/**
 * Created by lp on 24/01/2018.
 */

public interface ApiRequestComplete<T>
{
    void onResponse(String result) throws JSONException;
    void onError(String error);

}