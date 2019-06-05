package com.flickrsearch.util.decoder;

import com.flickrsearch.util.network.Result;
import java.io.InputStream;

public interface InputStreamDecoder<T> {
    Result<T> decodeStream(InputStream stream);
}
