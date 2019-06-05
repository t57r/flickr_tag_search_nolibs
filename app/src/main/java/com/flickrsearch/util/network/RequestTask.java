package com.flickrsearch.util.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.flickrsearch.util.decoder.InputStreamDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RequestTask<T> extends AsyncTask<String, Integer, Result<T>> {
    private static final int HTTP_TIMEOUT_MS = 3000;

    private final NetworkInfo networkInfo;
    private final InputStreamDecoder<T> inputStreamDecoder;
    private final OnResultListener<T> onResultListener;

    private String url;

    public RequestTask(
            NetworkInfo networkInfo,
            InputStreamDecoder<T> inputStreamDecoder,
            OnResultListener<T> onResultListener
    ) {
        this.networkInfo = networkInfo;
        this.inputStreamDecoder = inputStreamDecoder;
        this.onResultListener = onResultListener;
    }

    public String getUrl() {
        return url;
    }

    @Override
    protected void onPreExecute() {
        if (networkInfo == null ||
                !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            cancel(true);
        }
    }

    @Override
    protected Result<T> doInBackground(String... uri) {
        Result<T> result = null;
        if (!isCancelled() && uri != null && uri.length > 0) {
            try {
                url = uri[0];
                result = runGetRequest(new URL(url));
            } catch (Exception e) {
                result = new Result<>(e);
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Result<T> result) {
        if (result != null && onResultListener != null) {
            onResultListener.onResult(result);
        }
    }

    private Result<T> runGetRequest(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        Result<T> result = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(HTTP_TIMEOUT_MS);
            connection.setConnectTimeout(HTTP_TIMEOUT_MS);
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            stream = connection.getInputStream();
            if (stream != null) {
                result = inputStreamDecoder.decodeStream(stream);
            }
        } catch (Exception e) {
            result = new Result<>(e);
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }


}