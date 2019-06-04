package com.flickrsearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.flickrsearch.data.PhotosResponse;
import com.flickrsearch.data.mapper.JsonMapper;
import com.flickrsearch.data.mapper.PhotosResponseMapper;
import com.flickrsearch.util.network.OnResultListener;
import com.flickrsearch.util.network.RequestTask;
import com.flickrsearch.util.network.Result;

public class MainActivity extends AppCompatActivity {

    private static final String API_LINK = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text=kittens";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        JsonMapper<PhotosResponse> jsonMapper = new PhotosResponseMapper();
        OnResultListener<PhotosResponse> onResultListener = new OnResultListener<PhotosResponse>() {
            @Override
            public void onResult(Result<PhotosResponse> result) {
                Log.v("__DEBUG", "result.value = " + result.getValue());
                Log.v("__DEBUG", "result.exception = " + result.getException());
            }
        };

        RequestTask<PhotosResponse> requestTask = new RequestTask<>(networkInfo, jsonMapper, onResultListener);
        requestTask.execute(API_LINK);
    }
}
