package com.flickrsearch.util.decoder;

import android.support.annotation.NonNull;

import com.flickrsearch.data.mapper.JsonMapper;
import com.flickrsearch.util.network.Result;

import java.io.IOException;
import java.io.InputStream;

public class JsonInputStreamDecoder<T> implements InputStreamDecoder<T> {

    private static final int READ_BUFFER_SIZE = 4096;
    private final JsonMapper<T> jsonMapper;

    public JsonInputStreamDecoder(JsonMapper<T> jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public Result<T> decodeStream(@NonNull InputStream stream) {
        try {
            String responseBody = readStream(stream);
            return new Result<>(jsonMapper.map(responseBody));
        } catch (Exception e) {
            return new Result<>(e);
        }
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
