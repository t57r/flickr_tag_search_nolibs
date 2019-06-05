package com.flickrsearch.util.image;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.lang.ref.WeakReference;

class DownloadedDrawable extends ColorDrawable {
    private final WeakReference<ImageDownloadTask> imageDownloadTaskReference;

    DownloadedDrawable(ImageDownloadTask imageDownloadTask) {
        super(Color.GRAY);
        imageDownloadTaskReference = new WeakReference<>(imageDownloadTask);
    }

    ImageDownloadTask getImageDownloadTask() {
        return imageDownloadTaskReference.get();
    }
}