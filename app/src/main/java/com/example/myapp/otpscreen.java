package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class otpscreen extends AppCompatActivity {
    TextInputLayout phonenumbertext;
    TextInputEditText phonenumberinput;
    String email_pass;
    Button buverify;
    AlertDialog.Builder builder;
    Boolean abc;
    private SharedPreferences sharedPreferences, sp;
    private SharedPreferences.Editor editor;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpscreen);
        phonenumbertext = findViewById(R.id.phonenumbertext);
        phonenumberinput = findViewById(R.id.phonenumberinput);
        //email_pass=getIntent().getStringExtra("email_pass");
        sp = getSharedPreferences("mypref1", 0);
        email_pass = sp.getString("email_profile", "");
        buverify = findViewById(R.id.buverify);
        intent = new Intent(otpscreen.this, bottomnavigation.class);
        sharedPreferences = getSharedPreferences("mypref2", 0);
        editor = sharedPreferences.edit();

        boolean status = sharedPreferences.getBoolean("Is_Signed_phone", false);
        if (status) {
            startActivity(intent);
            finish();
        }


        buverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittextempty() == false) {
                    imagepost(email_pass);
                    serverinput(email_pass);
                    editor.putBoolean("Is_Signed_phone", true);
                    editor.apply();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public boolean edittextempty() {
        if (phonenumberinput.getText().toString().length() == 0) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Enter Phone Number")
                    .setPositiveButton("Ok", null);
            AlertDialog alert = builder.create();
            alert.show();
            abc = true;
        } else if (phonenumberinput.getText().toString().length() != 0) {
            abc = false;
        }
        return abc;
    }

    public void imagepost(String abf) {
        JSONObject jsonBody = new JSONObject();
        final String mRequestBody = jsonBody.toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:3000/api/signup/name/userprofile/" + abf, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "uploaded", Toast.LENGTH_LONG).show();
                Log.i("LOG_RESPONSE", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_RESPONSE", error.toString());
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
    }

    public void serverinput(String abf) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("phone_num", phonenumberinput.getText().toString());
            final String mRequestBody = jsonBody.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:3000/api/signup/name/phone_num/" + abf, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_RESPONSE", response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
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


