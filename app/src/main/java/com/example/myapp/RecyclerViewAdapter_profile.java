package com.example.myapp;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RecyclerViewAdapter_profile extends RecyclerView.Adapter<RecyclerViewAdapter_profile.MyViewHolder_profile> {
    private Context ncontext;
    RequestOptions option;
    private List<profile_item_user> ndata;
    AlertDialog.Builder builder;

    public RecyclerViewAdapter_profile(Context ncontext, List<profile_item_user> ndata) {
        this.ncontext = ncontext;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_image).error(R.drawable.loading_image);
        this.ndata = ndata;
    }

    @NonNull
    @Override
    public MyViewHolder_profile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(ncontext).inflate(R.layout.activity_profile_item, parent, false);
        MyViewHolder_profile vHolder1 = new MyViewHolder_profile(v);
        return vHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder_profile holder, final int position) {

        holder.thing_title1.setText(ndata.get(position).getThing_title1());
        holder.thing_price1.setText("₹"+ndata.get(position).getPrice1());
        final int abc = ndata.get(position).getItem_id();
        holder.busold_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ncontext);
                builder.setIcon(R.drawable.ic_baseline_delete_24);
                builder.setTitle("Confirm Deletion?");
                builder.setMessage("Delete when the item is sold or want to remove it")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                serverdelete(abc);
                                ndata.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,ndata.size());
                            }
                        }).setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();


            }
        });
        Glide.with(ncontext).load(ndata.get(position).getThumbnail1()).apply(option).into(holder.image_thing1);

    }

    @Override
    public int getItemCount() {
        return ndata.size();
    }

    public static class MyViewHolder_profile extends RecyclerView.ViewHolder {

        private TextView thing_title1;
        private TextView thing_price1;
        private ImageView image_thing1;
        private Button busold_out;




        public MyViewHolder_profile(@NonNull View itemView) {
            super(itemView);
            thing_title1 = itemView.findViewById(R.id.thing_title1);
            thing_price1 = itemView.findViewById(R.id.thing_price1);
            image_thing1 = itemView.findViewById(R.id.image_thing1);
            busold_out = itemView.findViewById(R.id.busold_out);

        }

    }


    public void serverdelete(int abf) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, "http://10.0.2.2:3000/api/signup/items/" + String.valueOf(abf),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ncontext, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        mysingleton.getInstance(ncontext).addToRequestQueue(stringRequest);
    }


}
