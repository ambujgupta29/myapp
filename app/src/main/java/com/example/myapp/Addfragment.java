package com.example.myapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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

import static android.app.Activity.RESULT_OK;


public class Addfragment extends Fragment {
    View v;
    TextView item_textview, description_textview, price_textview, category_textview, hostelblock_textview,thumbnail_img1;
    EditText item_edittext, description_edittext, price_edittext;
    Spinner spinner_category, spinner_hostelblock;
    ImageView image_item_main,image_item_main1,image_item_main2;
    Button bupublish;
    String category, hostel_block, email_get_final;
    private static final int image_pick_code=1000;
    private static final int image_pick_code1=1002;
    private static final int image_pick_code2=1003;
    private static final int permission_code=1001;
    boolean ab;
    int clicked=0;
    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add, container, false);
        item_textview = v.findViewById(R.id.item_textview);
        thumbnail_img1=v.findViewById(R.id.thumbnail_img1);
        description_textview = v.findViewById(R.id.description_textview);
        price_textview = v.findViewById(R.id.price_textview);
        category_textview = v.findViewById(R.id.category_textview);
        hostelblock_textview = v.findViewById(R.id.hostelblock_textview);
        item_edittext = v.findViewById(R.id.item_edittext);
        description_edittext = v.findViewById(R.id.description_edittext);
        price_edittext = v.findViewById(R.id.price_edittext);
        spinner_category = v.findViewById(R.id.spinner_category);
        spinner_hostelblock = v.findViewById(R.id.spinner_hostelblock);
        bupublish = v.findViewById(R.id.bupublish);
        image_item_main=v.findViewById(R.id.image_item_main);
        image_item_main1=v.findViewById(R.id.image_item_main1);
        image_item_main2=v.findViewById(R.id.image_item_main2);


        //spinner_category
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(adapter);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //spinner_hostel_blocks
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(v.getContext(), R.array.Hostel_Blocks, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hostelblock.setAdapter(adapter1);
        spinner_hostelblock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hostel_block = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //getting email
        bottomnavigation activity = (bottomnavigation) getActivity();
        email_get_final = activity.getmydata();


        bupublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittextempty() == false) {
                    Bitmap image1=((BitmapDrawable)image_item_main.getDrawable()).getBitmap();
                    Bitmap image2=((BitmapDrawable)image_item_main1.getDrawable()).getBitmap();
                    Bitmap image3=((BitmapDrawable)image_item_main2.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream1 =new ByteArrayOutputStream();
                    ByteArrayOutputStream byteArrayOutputStream2 =new ByteArrayOutputStream();
                    ByteArrayOutputStream byteArrayOutputStream3 =new ByteArrayOutputStream();
                    image1.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream1);
                    image2.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream2);
                    image3.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream3);
                    String encoded_image1=Base64.encodeToString(byteArrayOutputStream1.toByteArray(),Base64.DEFAULT);
                    String encoded_image2=Base64.encodeToString(byteArrayOutputStream2.toByteArray(),Base64.DEFAULT);
                    String encoded_image3=Base64.encodeToString(byteArrayOutputStream3.toByteArray(),Base64.DEFAULT);
                    serverinput(email_get_final, category, hostel_block,encoded_image1,encoded_image2,encoded_image3);

                }
            }
        });
        image_item_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked=1;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
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

        image_item_main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked=2;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions,permission_code);

                    }else {
                        pick_from_gallery1();

                    }
                }else {
                    pick_from_gallery1();
                }
            }
        });
        image_item_main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked=3;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions,permission_code);

                    }else {
                        pick_from_gallery2();

                    }
                }else {
                    pick_from_gallery2();
                }
            }
        });


        return v;
    }

    private void pick_from_gallery() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,image_pick_code);

    }

    private void pick_from_gallery1() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,image_pick_code1);
    }
    private void pick_from_gallery2() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,image_pick_code2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case permission_code:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(clicked==1) {
                        pick_from_gallery();
                    }
                    else if(clicked==2){
                        pick_from_gallery1();
                    }
                    else if (clicked==3){
                        pick_from_gallery2();
                    }
                }else {
                    Toast.makeText(getContext(),"permission denied..!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == image_pick_code){
            image_item_main.setImageURI(data.getData());
        }
        else if(resultCode == RESULT_OK && requestCode == image_pick_code1){
            image_item_main1.setImageURI(data.getData());

        }
        else if(resultCode == RESULT_OK && requestCode == image_pick_code2){
            image_item_main2.setImageURI(data.getData());

        }
    }



    public boolean edittextempty() {
        if (item_edittext.getText().toString().length() == 0 || price_edittext.getText().toString().length() == 0) {
            builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Please enter Item Details")
                    .setPositiveButton("Ok", null);
            AlertDialog alert = builder.create();
            alert.show();
            ab = true;
        } else if (item_edittext.getText().toString().length() != 0 && price_edittext.getText().toString().length() != 0) {
            ab = false;
        }
        return ab;
    }

    public void serverinput(String abf, String category_arg, String Hostel_block_arg,String encoded1,String encoded2,String encoded3) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("item", item_edittext.getText().toString());
            jsonBody.put("description", description_edittext.getText().toString());
            jsonBody.put("price", price_edittext.getText().toString());
            jsonBody.put("category", category_arg);
            jsonBody.put("photo_url1", encoded1);
            jsonBody.put("photo_url2", encoded2);
            jsonBody.put("photo_url3", encoded3);
            jsonBody.put("hostel_block", Hostel_block_arg);
            final String mRequestBody = jsonBody.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:3000/api/signup/name/user/" + abf, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.ic_baseline_check_circle_24);
                    builder.setTitle("Success!");
                    builder.setMessage("Item Published")
                            .setPositiveButton("Ok", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                    Log.i("LOG_RESPONSE", response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
                    Toast.makeText(v.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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

            mysingleton.getInstance(v.getContext()).addToRequestQueue(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
