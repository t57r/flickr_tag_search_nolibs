package com.flickrsearch.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.flickrsearch.R;
import com.flickrsearch.data.PhotosResponse;
import com.flickrsearch.data.mapper.JsonMapper;
import com.flickrsearch.data.mapper.PhotosResponseMapper;
import com.flickrsearch.util.NetworkInfoHelper;
import com.flickrsearch.util.network.OnResultListener;
import com.flickrsearch.util.network.RequestTask;
import com.flickrsearch.util.network.RequestTaskFactory;
import com.flickrsearch.util.network.Result;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String API_LINK = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text=kittens";

    private PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoAdapter = new PhotoAdapter(this, NetworkInfoHelper.getNetworkInfo(this));
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(photoAdapter);

        requestAPI();
    }

    private void requestAPI() {
        JsonMapper<PhotosResponse> jsonMapper = new PhotosResponseMapper();
        OnResultListener<PhotosResponse> onResultListener = new OnResultListener<PhotosResponse>() {
            @Override
            public void onResult(Result<PhotosResponse> result) {
                PhotosResponse response = result.getValue();
                if (response != null) {
                    photoAdapter.setPhotos(response.getPhotosPage().getPhotos());
                } else {

                }
            }
        };

        RequestTask<PhotosResponse> requestTask = RequestTaskFactory.createRequestTask(this,
                jsonMapper, onResultListener);
        requestTask.execute(API_LINK);
    }

}
