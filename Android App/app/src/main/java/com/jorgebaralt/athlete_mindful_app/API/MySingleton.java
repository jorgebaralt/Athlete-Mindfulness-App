package com.jorgebaralt.athlete_mindful_app.API;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by jorge on 9/4/2017.
 */

public class MySingleton{

    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingleton (Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return  requestQueue;

    }

    public static synchronized MySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new MySingleton(context);
        }

        return mInstance;

    }

    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }

}
