package com.example.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Homefragment extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    RecycleViewAdapter myrecylceviewadapter;
    SearchView search_view;
    List<thing> lst_thing;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        myrecyclerview = v.findViewById(R.id.recycler_view);
        search_view = v.findViewById(R.id.search_view);
        swipeRefreshLayout=v.findViewById(R.id.swiperefresh);
        myrecylceviewadapter = new RecycleViewAdapter(getContext(), lst_thing);
        myrecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        myrecyclerview.setAdapter(myrecylceviewadapter);

        serverget();
        searchfun();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               myrecylceviewadapter.notifyDataSetChanged();
               swipeRefreshLayout.setRefreshing(false);
            }
        });
        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lst_thing = new ArrayList<>();

    }

    public void searchfun() {
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myrecylceviewadapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    public void serverget() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:3000/api/signup/name/home1", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int count = 0; count < response.length(); count++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                thing thin = new thing();
                                thin.setPrice(jsonObject.getString("price"));
                                thin.setItem_id(jsonObject.getString("item_id"));
                                thin.setThing_title(jsonObject.getString("item_name"));
                                thin.setImage_url1("http://10.0.2.2:3000/"+jsonObject.getString("photo_url1"));
                                thin.setImage_url2("http://10.0.2.2:3000/"+jsonObject.getString("photo_url2"));
                                thin.setImage_url3("http://10.0.2.2:3000/"+jsonObject.getString("photo_url3"));
                                thin.setHostel_block(jsonObject.getString("hostel_block"));
                                thin.setCategory(jsonObject.getString("category"));
                                thin.setDescription(jsonObject.getString("description"));
                                lst_thing.add(thin);
                                myrecylceviewadapter.notifyItemInserted(lst_thing.size()-1);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(v.getContext(), "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });


        mysingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

    }


}
