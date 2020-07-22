package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class moreinfo extends AppCompatActivity {

    private static final int REQUEST_CALL=178;
    TextView tvprice, tvdescription, tvcategory,tvhostel_block,name_user,phone_num_user,tvdescriptionstatic,tvcategorystatic,tvhostel_blockstatic;
    ImageView call;
    CircleImageView circleImageView;
    SliderView sliderView;
    SliderAdapterExample adapter;
    phone_item ph=new phone_item();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfo);
        tvprice =  findViewById(R.id.tvprice);
        tvdescription =  findViewById(R.id.tvdescription);
        tvcategory =  findViewById(R.id.tvcategory);
        tvhostel_block =  findViewById(R.id.tvhostel_block);
        tvdescriptionstatic =  findViewById(R.id.tvdescriptionstatic);
        tvcategorystatic =  findViewById(R.id.tvcategorystatic);
        tvhostel_blockstatic =  findViewById(R.id.tvhostel_blockstatic);
        name_user =  findViewById(R.id.name_user);
        phone_num_user =  findViewById(R.id.phone_num_user);
        call =  findViewById(R.id.call);
        circleImageView=findViewById(R.id.profile_image2);




        Intent intent = getIntent();
        String price = intent.getExtras().getString("price");
        String hostel_block = intent.getExtras().getString("hostel_block");
        String category = intent.getExtras().getString("category");
        String Description = intent.getExtras().getString("Description");
        String image_url1 = intent.getExtras().getString("image_url1");
        String image_url2 = intent.getExtras().getString("image_url2");
        String image_url3 = intent.getExtras().getString("image_url3");
        String item_id = intent.getExtras().getString("item_id");
       profileget(item_id);
        profilegetname(item_id);
        profilegetphone_num(item_id);

        sliderView = findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(this);

        adapter.addItem(new SliderItem("demo1",image_url1));
        adapter.addItem(new SliderItem("demo1",image_url2));
        adapter.addItem(new SliderItem("demo1",image_url3));
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();



        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });


        tvprice.setText("₹" + price);
        tvcategory.setText(category);
        tvdescription.setText(Description);
        tvhostel_block.setText(hostel_block);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makephonecall();
            }
        });

    }
    public void profileget(String item_idget) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:3000/api/signup/items/image/"+item_idget, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int count = 0; count < response.length(); count++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                Glide.with(getApplicationContext()).load("http://10.0.2.2:3000/"+jsonObject.getString("profile_pic_url")).into(circleImageView);



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

    public void profilegetname(String item_idget) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:3000/api/signup/items/image/name/"+item_idget, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int count = 0; count < response.length(); count++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                name_user.setText(jsonObject.getString("name"));



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

    public void profilegetphone_num(String item_idget) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:3000/api/signup/items/image/phone/"+item_idget, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int count = 0; count < response.length(); count++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                phone_num_user.setText("+91"+jsonObject.getString("phone_num"));
                                ph.setPhone("+91"+jsonObject.getString("phone_num"));



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
    public void makephonecall(){
        String number=ph.getPhone();
        if(ContextCompat.checkSelfPermission(moreinfo.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(moreinfo.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else{
            String dial="tel:"+number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if(requestCode==REQUEST_CALL)
      {
          if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
              makephonecall();
          }else
              Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();
      }
    }
}
