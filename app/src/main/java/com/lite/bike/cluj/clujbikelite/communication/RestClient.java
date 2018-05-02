package com.lite.bike.cluj.clujbikelite.communication;

import com.google.gson.Gson;
import com.lite.bike.cluj.clujbikelite.model.StationsData;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class RestClient {

    public RestClient(Context _context){
        requestQueue = Volley.newRequestQueue(_context);
    }

    private String ip = "portal.clujbike.eu/Station/Read";
    private String port = "";//":8080";
    private String protocol = "http";
    private RequestQueue requestQueue;

    public void GetStationsData(Response.Listener responseListener, Response.ErrorListener errorListener)
    {
        String url = protocol + "://" + ip + "" + port;
        CustomRequest c = new CustomRequest(Request.Method.POST, url, responseListener,errorListener);
        requestQueue.add(c);
    }

}
