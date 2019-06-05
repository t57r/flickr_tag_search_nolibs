package com.flickrsearch.util.image;

import android.graphics.drawable.Drawable;
import android.net.NetworkInfo;
import android.widget.ImageView;

public class ImageDownloader {

    public static void download(NetworkInfo networkInfo, String url, ImageView imageView) {
        if (cancelPotentialDownload(url, imageView)) {
            ImageDownloadTask task = new ImageDownloadTask(networkInfo, imageView);
            DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
            imageView.setImageDrawable(downloadedDrawable);
            task.execute(url);
        }
    }

    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        ImageDownloadTask imageDownloadTask = getImageDownloadTask(imageView);
        if (imageDownloadTask != null) {
            String bitmapUrl = imageDownloadTask.getUrl();
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                imageDownloadTask.cancel(true);
            } else {
                return false; // The same URL is already being downloaded
            }
        }
        return true;
    }

    static ImageDownloadTask getImageDownloadTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
                return downloadedDrawable.getImageDownloadTask();
            }
        }
        return null;
    }

}
