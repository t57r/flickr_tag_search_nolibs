package com.flickrsearch.util.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.flickrsearch.data.mapper.JsonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RequestTask<T> extends AsyncTask<String, Integer, Result<T>> {
    private static final int HTTP_TIMEOUT_MS = 3000;
    private static final int READ_BUFFER_SIZE = 4096;

    private final NetworkInfo networkInfo;
    private final JsonMapper<T> jsonMapper;
    private final OnResultListener<T> onResultListener;

    public RequestTask(
            NetworkInfo networkInfo,
            JsonMapper<T> jsonMapper,
            OnResultListener<T> onResultListener
    ) {
        this.networkInfo = networkInfo;
        this.jsonMapper = jsonMapper;
        this.onResultListener = onResultListener;
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
                URL url = new URL(uri[0]);
                String responseBody = runGetRequest(url);
                if (responseBody != null) {
                    result = new Result<>(jsonMapper.map(responseBody));
                } else {
                    throw new IOException("No response received.");
                }
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

    private String runGetRequest(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;
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
                result = readStream(stream);
            }
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

    private String readStream(InputStream stream) throws IOException {
        byte[] buffer = new byte[READ_BUFFER_SIZE];
        StringBuilder strBuilder = new StringBuilder();
        int readBytes;
        while ((readBytes = stream.read(buffer)) >= 0) {
            strBuilder.append(new String(buffer, 0, readBytes));
        }
        return strBuilder.toString();
    }
}