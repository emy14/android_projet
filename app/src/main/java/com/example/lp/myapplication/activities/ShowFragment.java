package com.example.lp.myapplication.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.example.lp.myapplication.R;
import com.example.lp.myapplication.adapter.NotesAdapter;
import com.example.lp.myapplication.extensions.ApiRequest;
import com.example.lp.myapplication.interfaces.ApiRequestComplete;
import com.example.lp.myapplication.models.Notes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lp on 24/01/2018.
 */

public class ShowFragment extends Fragment {

    private ArrayList<Notes> items;
    private ListView listView;
    private NotesAdapter itemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_activity,
                container, false);

        listView = (ListView) rootView.findViewById(R.id.list);

        items = new ArrayList<>();
        itemsAdapter = new NotesAdapter(getActivity(), R.layout.adapter_notes, items);
//        itemsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);

        getNotes();

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getNotes() {

        ApiRequest.getInstance().request(Request.Method.GET, "show/1", new ApiRequestComplete<String>() {
            @Override
            public void onResponse(String result) throws JSONException {
                JSONArray notes = new JSONArray(result);

                for(int numberNotes = 0 ; numberNotes < notes.length() ; numberNotes ++){
                   JSONObject note = notes.getJSONObject(numberNotes);
                   Notes n = new Notes( note.getInt("note") ,  note.getInt("coeff"),  note.getInt("quotient"),  note.getString("nom_matiere")  );
//                    items.add(note.getString("nom_matiere") + " : " + note.getString("note") + "/" + note.getString("quotient") + " - Coeff : " + note.getString("coeff"));
                      items.add(n);
                }

                listView.setAdapter(itemsAdapter);

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
