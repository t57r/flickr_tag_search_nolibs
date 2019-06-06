package com.flickrsearch.util.image;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.util.LruCache;

class ImageCache {
    private static final int CACHE_SIZE = 50 * 1024 * 1024; // 50MiB

    private final LruCache<String, Bitmap> bitmapCache = new LruCache<String, Bitmap>(CACHE_SIZE) {
        protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
            return BitmapCompat.getAllocationByteCount(value);
        }
    };

    void put(String url, Bitmap bitmap) {
        if (url == null || bitmap == null) {
            throw new NullPointerException("Either url or bitmap is null");
        }

        if (bitmapCache.get(url) == null) {
            bitmapCache.put(url, bitmap);
        }
    }

    Bitmap get(String url) {
        return bitmapCache.get(url);
    }
}
