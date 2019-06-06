package com.flickrsearch.util.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

class ImageViewUtils {
    private static final int ANIMATION_DURATION = 400;

    static void fadeInBitmap(ImageView imageView, Bitmap bitmap) {
        Drawable[] layers = new Drawable[2];
        layers[0] = imageView.getDrawable();
        layers[1] = new BitmapDrawable(imageView.getResources(), bitmap);

        TransitionDrawable transitionDrawable = new TransitionDrawable(layers);
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(ANIMATION_DURATION);
    }

}
