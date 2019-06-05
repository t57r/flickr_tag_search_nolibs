package com.flickrsearch.util.decoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.flickrsearch.util.network.Result;

import java.io.IOException;
import java.io.InputStream;

public class BitmapInputStreamDecoder implements InputStreamDecoder<Bitmap> {
    @Override
    public Result<Bitmap> decodeStream(InputStream stream) {
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return bitmap != null
                ? new Result<>(bitmap)
                : new Result<Bitmap>(new IOException("The image data could not be decoded"));
    }
}
