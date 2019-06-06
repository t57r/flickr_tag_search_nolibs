package com.flickrsearch.presentation;

import com.flickrsearch.Configuration;
import com.flickrsearch.data.PhotosPage;
import com.flickrsearch.data.PhotosResponse;
import com.flickrsearch.data.mapper.JsonMapper;
import com.flickrsearch.data.mapper.PhotosResponseMapper;
import com.flickrsearch.util.network.OnResultListener;
import com.flickrsearch.util.network.RequestTask;
import com.flickrsearch.util.network.RequestTaskFactory;
import com.flickrsearch.util.network.Result;

public class MainPresenter implements MainContract.Presenter {
    private static final JsonMapper<PhotosResponse> PHOTOS_RESPONSE_MAPPER = new PhotosResponseMapper();

    private final MainContract.View view;

    private RequestTask<PhotosResponse> requestTask;
    private String query;
    private long pageNumber = 1;
    private boolean isLoading = false;
    private boolean hasMoreItems = false;

    MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void cancelLoading() {
        if (requestTask != null) {
            isLoading = false;
            requestTask.cancel(true);
        }
    }

    @Override
    public void loadFirstPageOfPhotos(String query) {
        pageNumber = 1;
        this.query = query;
        loadPhotos();
    }

    @Override
    public void loadNextPageOfPhotos() {
        ++pageNumber;
        loadPhotos();
    }

    private void loadPhotos() {
        final OnResultListener<PhotosResponse> onResultListener = new OnResultListener<PhotosResponse>() {
            @Override
            public void onResult(Result<PhotosResponse> result) {
                PhotosResponse response = result.getValue();
                if (response != null) {
                    PhotosPage photosPage = response.getPhotosPage();
                    pageNumber = photosPage.getPage();
                    hasMoreItems = pageNumber != photosPage.getPages();
                    boolean shouldClear = pageNumber == 1;
                    view.setPhotos(photosPage.getPhotos(), shouldClear);
                } else {
                    Exception exception = result.getException();
                    String errorMsg = exception != null ? exception.getMessage() : "Something went wrong";
                    view.showError(errorMsg);
                }
                isLoading = false;
            }
        };
        isLoading = true;
        requestTask = RequestTaskFactory.createRequestTask(view.getNetworkInfo(),
                PHOTOS_RESPONSE_MAPPER, onResultListener);
        requestTask.execute(Configuration.createFlickrGetPhotosUrl(query, pageNumber));
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public boolean hasMoreItems() {
        return hasMoreItems;
    }
}
