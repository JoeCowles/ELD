package com.carriergistics.eld.apiData;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carriergistics.eld.MainActivity;

public class ApiConnector {


    private static String serverUrl;
    private static Activity mContext;
    public static void init(String url, Activity context) {
        serverUrl = url;
        mContext  = context;

    }

    public static String request(String request){
        RequestQueue queue = MainActivity.newRequest();
        StringRequest req = new StringRequest(Request.Method.GET, serverUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error response
                    }
                }
        );
        queue.add(req);
        return "";

    }

    public void proccessData(){

    }
}
