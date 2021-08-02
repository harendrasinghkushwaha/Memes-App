package com.example.dailymemes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MemeAdapter.OnItemClickListener{
    ViewPager2 viewPager2;
    List<Meme> memes;
    MemeAdapter adapter;
    private static final String MEME_API = "https://meme-api.herokuapp.com/gimme/50";
    private TextView mEmptyStateTextView;
    private static final int PERMISSION_STORAGE_CODE = 1000;
    String downloadurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.list);
        memes = new ArrayList<>();
        View loadingIndicator = findViewById(R.id.loading_indicator);

        if(checkConnection()){
            extractMemes();
        }else{
            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            loadingIndicator.setVisibility(View.GONE);
        }
    }

    //check connection
    private boolean checkConnection(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            return true;
        }else{
            return false;
        }

    }
    private void extractMemes() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, MEME_API, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("memes");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject memeobject = jsonArray.getJSONObject(i);
                        Meme meme = new Meme();
                        meme.setMeme(memeobject.getString("url").toString());
                        memes.add(meme);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new MemeAdapter(getApplicationContext(),memes);
                viewPager2.setAdapter(adapter);
                adapter.setOnItemClickListener(MainActivity.this);
                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorResponse: "+error.getMessage());

            }
        });
        queue.add(jsonObjectRequest);
    }
    @Override
    public void onItemClick(int position) {
        //Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Hey checkout this cool meme:)");
        sendIntent.putExtra(Intent.EXTRA_TEXT,memes.get(position).getMeme() );


        Intent shareIntent = Intent.createChooser(sendIntent, "Share this meme using...");
        startActivity(shareIntent);
    }
    @Override
    public void downloadImage(int position) {
        downloadurl = memes.get(position).getMeme();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                String pemissions = (Manifest.permission.WRITE_EXTERNAL_STORAGE);
                requestPermissions(new String[]{pemissions},PERMISSION_STORAGE_CODE);

            }else{
                startDownloading();

            }
        }else{
            startDownloading();

        }
    }
    private void startDownloading() {
        String url = downloadurl.toString().trim();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI) | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("downlad");
        request.setDescription("downloading...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+ System.currentTimeMillis());


        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startDownloading();
                }else{
                    Toast.makeText(this,"permission denide",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}