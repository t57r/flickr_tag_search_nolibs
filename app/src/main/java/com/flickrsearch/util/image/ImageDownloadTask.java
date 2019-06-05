package com.flickrsearch.util.image;

import android.graphics.Bitmap;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.flickrsearch.util.decoder.BitmapInputStreamDecoder;
import com.flickrsearch.util.decoder.InputStreamDecoder;
import com.flickrsearch.util.network.RequestTask;
import com.flickrsearch.util.network.Result;

import java.lang.ref.WeakReference;

public class ImageDownloadTask extends RequestTask<Bitmap> {

    private static final InputStreamDecoder<Bitmap> BITMAP_DECODER = new BitmapInputStreamDecoder();
    private final WeakReference<ImageView> imageViewReference;

    ImageDownloadTask(NetworkInfo networkInfo, ImageView imageView) {
        super(networkInfo, BITMAP_DECODER, null);
        imageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected void onPostExecute(Result<Bitmap> result) {
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            ImageDownloadTask imageDownloadTask = ImageDownloader.getImageDownloadTask(imageView);

            // Change bitmap only if this process is still associated with it
            Bitmap bitmap = result.getValue();
            if (this == imageDownloadTask && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
