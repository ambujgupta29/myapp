package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import de.hdodenhof.circleimageview.CircleImageView;

public class editprofile extends AppCompatActivity {
    CircleImageView circleImageView;
    Button buupload;
    private static final int image_pick_code=1010;
    private static final int permission_code=1011;
    SharedPreferences sp;
    String email_get;
    SharedPreferences.Editor editor;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        circleImageView=findViewById(R.id.profile_image1);
        buupload=findViewById(R.id.buupload);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions,permission_code);

                    }else {
                        pick_from_gallery();

                    }
                }else {
                    pick_from_gallery();
                }
            }
        });
        buupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap profileimg=((BitmapDrawable)circleImageView.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream5 =new ByteArrayOutputStream();
                profileimg.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream5);
                String encoded_image_profile_pic= Base64.encodeToString(byteArrayOutputStream5.toByteArray(),Base64.DEFAULT);
                sp = getSharedPreferences("mypref1", 0);
                email_get=sp.getString("email_profile", "");
                imagepost(encoded_image_profile_pic,email_get);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case permission_code:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pick_from_gallery();
                }else {
                    Toast.makeText(getApplicationContext(),"permission denied..!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private void pick_from_gallery() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,image_pick_code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == image_pick_code) {
            circleImageView.setImageURI(data.getData());
        }

    }


    public void imagepost(String profilepic, String abf)
    {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("profile_pic", profilepic);
            final String mRequestBody = jsonBody.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.PATCH, "http://10.0.2.2:3000/api/signup/name/userprofile/update/" + abf, new Response.Listener<String>() {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}