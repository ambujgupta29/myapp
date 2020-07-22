package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profilefragment extends Fragment {
    private RecyclerView recyclerView_profile;
    String email_get_final;
    CircleImageView circleImageView;
    TextView tv_name1;
    Button profile_user;
    RecyclerViewAdapter_profile recyclerViewAdapter_profile;
    private List<profile_item_user>lst_item_profile;
    View c;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        c= inflater.inflate(R.layout.fragment_profile, container, false);
        circleImageView=c.findViewById(R.id.profile_image);
        tv_name1=c.findViewById(R.id.profile_name);
        profile_user=c.findViewById(R.id.profile_user);
        recyclerView_profile=c.findViewById(R.id.recycler_view_profile);
        recyclerViewAdapter_profile=new RecyclerViewAdapter_profile(getContext(),lst_item_profile);
        recyclerView_profile.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_profile.setAdapter(recyclerViewAdapter_profile);



        bottomnavigation activity = (bottomnavigation) getActivity();
        email_get_final = activity.getmydata();

        serverget(email_get_final);
        profileget(email_get_final);
        profilegetname(email_get_final);
        profile_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),editprofile.class);
                startActivity(intent);
            }
        });

        return c;




    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lst_item_profile=new ArrayList<>();

    }

    public void serverget(String emailget) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:3000/api/signup/name/profile/"+emailget, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int count = 0; count < response.length(); count++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                profile_item_user profileItemUser=new profile_item_user();
                                profileItemUser.setPrice1(jsonObject.getString("price"));
                                profileItemUser.setThing_title1(jsonObject.getString("item_name"));
                                profileItemUser.setThumbnail1("http://10.0.2.2:3000/"+jsonObject.getString("photo_url1"));
                                profileItemUser.setItem_id(jsonObject.getInt("item_id"));
                                lst_item_profile.add(profileItemUser);
                                recyclerViewAdapter_profile.notifyItemInserted(lst_item_profile.size()-1);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        mysingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    public void profileget(String emailget) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:3000/api/signup/name/profileget/"+emailget, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int count = 0; count < response.length(); count++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                Glide.with(getContext()).load("http://10.0.2.2:3000/"+jsonObject.getString("profile_pic_url")).into(circleImageView);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        mysingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    public void profilegetname(String emailget) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:3000/api/signup/name/profileget/name/"+emailget, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int count = 0; count < response.length(); count++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                tv_name1.setText(jsonObject.getString("name"));



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        mysingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

}