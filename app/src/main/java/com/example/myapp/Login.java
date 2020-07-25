package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    AlertDialog.Builder builder;
    Button buverify;
    TextInputLayout phonenumbertext;
    TextInputEditText phonenumberinput;
    String email_pass;
    Boolean abc;
    String email_get_email;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    Loginget le = new Loginget();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpscreen);
        phonenumbertext = findViewById(R.id.phonenumbertext);
        phonenumberinput = findViewById(R.id.phonenumberinput);
        email_pass = getIntent().getStringExtra("email_pass_value");
        buverify = findViewById(R.id.buverify);
        sp = getSharedPreferences("mypref3", 0);
        edit = sp.edit();
        buverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittextempty() == false) {

                    profilegetphone_num(phonenumberinput.getText().toString());
                    Log.i("nameyy", phonenumberinput.getText().toString());


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            email_get_email = le.getEmail();
                            Log.i("nameyyikik", email_get_email);
                            edit.putBoolean("Is_Signed", true);
                            edit.putString("email_pass2", email_get_email);
                            edit.apply();


                            Intent intent = new Intent(Login.this, bottomnavigation.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 3000);
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

    public void profilegetphone_num(String phone_num) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:3000/api/signup/items/login/" + phone_num, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int count = 0; count < response.length(); count++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                Log.i("emil", jsonObject.getString("email"));
                                le.setEmail(jsonObject.getString("email"));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        mysingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);

    }

}
