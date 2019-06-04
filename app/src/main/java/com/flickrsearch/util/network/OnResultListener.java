package com.flickrsearch.util.network;

public interface OnResultListener<T> {
    void onResult(Result<T> result);
}