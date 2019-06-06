package com.flickrsearch.util.image;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.lang.ref.WeakReference;

class DownloadedDrawable extends ColorDrawable {
    private static final int PLACEHOLDER_COLOR = Color.parseColor("#f7f7f7");
    private final WeakReference<ImageDownloadTask> imageDownloadTaskReference;

    DownloadedDrawable(ImageDownloadTask imageDownloadTask) {
        super(PLACEHOLDER_COLOR);
        imageDownloadTaskReference = new WeakReference<>(imageDownloadTask);
    }

    ImageDownloadTask getImageDownloadTask() {
        return imageDownloadTaskReference.get();
    }
}