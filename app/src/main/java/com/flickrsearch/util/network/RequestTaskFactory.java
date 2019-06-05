package com.flickrsearch.util.network;

import android.app.Activity;
import android.net.NetworkInfo;

import com.flickrsearch.data.mapper.JsonMapper;
import com.flickrsearch.util.NetworkInfoHelper;
import com.flickrsearch.util.decoder.InputStreamDecoder;
import com.flickrsearch.util.decoder.JsonInputStreamDecoder;

public class RequestTaskFactory {

    public static <T> RequestTask<T> createRequestTask(
            Activity activity,
            JsonMapper<T> jsonMapper,
            OnResultListener<T> onResultListener
    ) {
        NetworkInfo networkInfo = NetworkInfoHelper.getNetworkInfo(activity);
        InputStreamDecoder<T> streamDecoder = new JsonInputStreamDecoder<>(jsonMapper);
        return new RequestTask<>(networkInfo, streamDecoder, onResultListener);
    }

}
