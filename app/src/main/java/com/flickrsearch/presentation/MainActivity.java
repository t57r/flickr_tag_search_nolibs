package com.flickrsearch.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;

import com.flickrsearch.R;
import com.flickrsearch.data.PhotosResponse;
import com.flickrsearch.data.mapper.JsonMapper;
import com.flickrsearch.data.mapper.PhotosResponseMapper;
import com.flickrsearch.util.NetworkInfoHelper;
import com.flickrsearch.util.network.EditTextDebouncer;
import com.flickrsearch.util.network.OnResultListener;
import com.flickrsearch.util.network.RequestTask;
import com.flickrsearch.util.network.RequestTaskFactory;
import com.flickrsearch.util.network.Result;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String API_LINK = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text=";

    private PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoAdapter = new PhotoAdapter(this, NetworkInfoHelper.getNetworkInfo(this));
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(photoAdapter);

        final JsonMapper<PhotosResponse> jsonMapper = new PhotosResponseMapper();
        final OnResultListener<PhotosResponse> onResultListener = new OnResultListener<PhotosResponse>() {
            @Override
            public void onResult(Result<PhotosResponse> result) {
                PhotosResponse response = result.getValue();
                if (response != null) {
                    photoAdapter.setPhotos(response.getPhotosPage().getPhotos());
                } else {

                }
            }
        };

        EditText searchEt = findViewById(R.id.searchEt);
        EditTextDebouncer editTextDebouncer = new EditTextDebouncer();
        editTextDebouncer.debounce(searchEt, new EditTextDebouncer.DebounceListener() {
            private RequestTask<PhotosResponse> requestTask;

            @Override
            public void onCancel() {
                if (requestTask != null) {
                    requestTask.cancel(true);
                }
            }

            @Override
            public void onRun(String text) {
                requestTask = RequestTaskFactory.createRequestTask(MainActivity.this,
                        jsonMapper, onResultListener);
                String requestUrl = API_LINK + text;
                requestTask.execute(requestUrl);
            }
        });

    }

}
