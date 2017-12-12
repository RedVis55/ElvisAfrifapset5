package com.example.elvis.elvisafrifapset8;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends android.support.v4.app.ListFragment {

    ArrayList<String> myArray = new ArrayList<>();
    ArrayList<Double> dishprice = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();
        final String category = args.getString("category");

        String url = "https://resto.mprog.nl/menu";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest JsonObj = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    private JSONObject jsonObject = null;
                    private JSONArray jsonArray = null;

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jsonObject = new JSONObject(response.toString());
                            jsonArray = jsonObject.getJSONArray("items");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                if (jsonArray.getJSONObject(i).optString("category").equals(category)) {
                                    myArray.add(jsonArray.getJSONObject(i).optString("name"));
                                    dishprice.add(jsonArray.getJSONObject(i).optDouble("price"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        setAdapter();

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

        };
        queue.add(JsonObj);


    }



    private void setAdapter(){
        ArrayAdapter<String> myAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myArray);

        this.setListAdapter(myAdapter);
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_menu, container, false);
//    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        String itemclicked = (String) l.getItemAtPosition(position);
//
//        RestoDatabase db = RestoDatabase.getInstance(getContext());
//    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        RestoDatabase db = RestoDatabase.getInstance(getContext());

        String menuClicked = myArray.get((int)id) + " added to the list";
        Toast.makeText(getContext(), menuClicked, Toast.LENGTH_SHORT).show();

        db.addItem(myArray.get((int)id), dishprice.get((int)id),  id);
    }
}

