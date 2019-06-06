package com.flickrsearch.presentation;

import android.net.NetworkInfo;

import com.flickrsearch.data.Photo;

import java.util.List;

public interface MainContract {

    interface View {
        void setPhotos(List<Photo> photos, boolean shouldClear);
        void showError(String message);
        NetworkInfo getNetworkInfo();
    }

    interface Presenter {
        void cancelLoading();
        void loadFirstPageOfPhotos(String query);
        void loadNextPageOfPhotos();
        boolean isLoading();
        boolean hasMoreItems();
    }

}
