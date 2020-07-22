package com.example.myapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class mysingleton {
    private static  mysingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private mysingleton(Context context)
    {
        mCtx=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized mysingleton getInstance(Context context){
        if(mInstance==null)
        {
            mInstance=new mysingleton(context);
        }
        return mInstance;

    }
    public void addToRequestQueue(Request request)
    {
        requestQueue.add(request);
    }
}
