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
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.example.lp.myapplication.R;
import com.example.lp.myapplication.adapter.NotesAdapter;
import com.example.lp.myapplication.extensions.ApiRequest;
import com.example.lp.myapplication.interfaces.ApiRequestComplete;
import com.example.lp.myapplication.models.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lp on 24/01/2018.
 */

public class ShowFragment extends Fragment {

    private ArrayList<Note> items;
    private ListView listView;
    private NotesAdapter itemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_activity,
                container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(mUpdateListener);
        items = new ArrayList<>();
        itemsAdapter = new NotesAdapter(getActivity(), R.layout.adapter_notes, items);

        getNotes();

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


    public void getNotes() {

        ApiRequest.getInstance().request(Request.Method.GET, "show/1", new ApiRequestComplete<String>() {
            @Override
            public void onResponse(String result) throws JSONException {
                JSONArray notes = new JSONArray(result);

                for(int numberNotes = 0 ; numberNotes < notes.length() ; numberNotes ++){
                   JSONObject note = notes.getJSONObject(numberNotes);
                   Note n = new Note(note.getInt("id_note"), note.getInt("note") ,  note.getInt("coeff"),  note.getInt("quotient"),  note.getString("nom_matiere"), note.getInt("id_matiere")  );
                   items.add(n);
                }

                listView.setAdapter(itemsAdapter);

            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private AdapterView.OnItemClickListener mUpdateListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Note item = (Note) parent.getItemAtPosition(position);

            Intent intent = new Intent(getActivity(), UpdateActivity.class);
            intent.putExtra("id", item.getId().toString());
            intent.putExtra("note", item.getNotes().toString());
            intent.putExtra("quotient", item.getQuotient().toString());
            intent.putExtra("coeff", item.getCoeff().toString());
            intent.putExtra("matiere", item.getMatiere().toString());
            intent.putExtra("id_matiere", item.getId_matiere());

            startActivity(intent);

        }
    };
}
