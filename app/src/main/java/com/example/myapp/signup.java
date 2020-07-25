package com.example.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class signup extends AppCompatActivity {
    TextView textview2;
    TextInputLayout name, email;
    TextInputEditText emailinput, nameinput;
    Button bunext, bulogin;
    Boolean ab;
    ProgressDialog progressDialog;
    ImageView img_icon;
    AlertDialog.Builder builder;
    String url = "http://10.0.2.2:3000/api/signup";
    private SharedPreferences sp, sp2;
    private SharedPreferences.Editor edit, editor;
    Intent intent, intent1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        img_icon = findViewById(R.id.img_icon);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        bunext = findViewById(R.id.bunext);
        bulogin = findViewById(R.id.bulogin);
        textview2 = findViewById(R.id.textview2);
        emailinput = findViewById(R.id.emailinput);
        nameinput = findViewById(R.id.nameinput);
        intent = new Intent(signup.this, otpscreen.class);
        sp = getSharedPreferences("mypref1", 0);
        edit = sp.edit();
        progressDialog = new ProgressDialog(this);

        sp2 = getSharedPreferences("mypref3", 0);
        editor = sp2.edit();
        intent1 = new Intent(signup.this, bottomnavigation.class);
        boolean status1 = sp2.getBoolean("Is_Signed", false);
        if (status1) {
            startActivity(intent1);
        }

        boolean status = sp.getBoolean("Is_Signed", false);
        if (status) {
            startActivity(intent);
        }
        bunext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittextempty() == false) {
                    serverinput();
                    edit.putBoolean("Is_Signed", true);
                    edit.putString("email_profile", emailinput.getText().toString());
                    edit.putString("name_profile", nameinput.getText().toString());
                    edit.apply();
                    startActivity(intent);

                }
            }
        });

        bulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailvalue = emailinput.getText().toString();
                emailvalue = "";
                Intent intent1 = new Intent(signup.this, Login.class);
                intent1.putExtra("email_pass_value", emailvalue);
                startActivity(intent1);
            }
        });
    }

    public boolean edittextempty() {
        if (nameinput.getText().toString().length() == 0 || emailinput.getText().toString().length() == 0) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Fill The Details")
                    .setPositiveButton("Ok", null);
            AlertDialog alert = builder.create();
            alert.show();
            ab = true;
        } else if (nameinput.getText().toString().length() != 0 && emailinput.getText().toString().length() != 0) {
            ab = false;
        }
        return ab;
    }

    public void serverinput() {
        try {
            progressDialog.setMessage("please wait...");
            progressDialog.show();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", nameinput.getText().toString());
            jsonBody.put("email", emailinput.getText().toString());
            final String mRequestBody = jsonBody.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_RESPONSE", response);
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            mysingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

