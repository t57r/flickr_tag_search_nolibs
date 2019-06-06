package com.flickrsearch.util.image;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

class ImageCache {

    private static final int ENTRY_COUNT_MAX = 500;
    private final LruCache<String, Bitmap> bitmapCache = new LruCache<>(ENTRY_COUNT_MAX);

    void put(@NonNull String url, @NonNull Bitmap bitmap) {
        if (bitmapCache.get(url) == null) {
            bitmapCache.put(url, bitmap);
        }
    }

    Bitmap get(String url) {
        return bitmapCache.get(url);
    }
}
