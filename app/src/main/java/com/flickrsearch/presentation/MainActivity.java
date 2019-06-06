package com.flickrsearch.presentation;

import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.flickrsearch.R;
import com.flickrsearch.data.Photo;
import com.flickrsearch.util.NetworkInfoHelper;
import com.flickrsearch.util.network.EditTextDebouncer;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MainContract.View,
        AbsListView.OnScrollListener,
        EditTextDebouncer.DebounceListener {

    private MainContract.Presenter presenter;
    private PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        photoAdapter = new PhotoAdapter(this, getNetworkInfo());
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(photoAdapter);
        gridView.setOnScrollListener(this);

        EditText searchEt = findViewById(R.id.searchEt);
        EditTextDebouncer editTextDebouncer = new EditTextDebouncer();
        editTextDebouncer.debounce(searchEt, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.cancelLoading();
    }

    @Override
    public void setPhotos(List<Photo> photos, boolean shouldClear) {
        photoAdapter.setPhotos(photos, shouldClear);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public NetworkInfo getNetworkInfo() {
        return NetworkInfoHelper.getNetworkInfo(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount > 0) {
            int lastVisibleItem = firstVisibleItem + visibleItemCount;
            if (!presenter.isLoading() && presenter.hasMoreItems() && (lastVisibleItem == totalItemCount)) {
                presenter.loadNextPageOfPhotos();
            }
        }
    }

    @Override
    public void onCancel() {
        presenter.cancelLoading();
    }

    @Override
    public void onTextChanged(String text) {
        presenter.loadFirstPageOfPhotos(text);
    }
}
