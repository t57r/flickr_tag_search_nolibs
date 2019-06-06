package com.flickrsearch.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.Random;

import static android.graphics.Bitmap.Config.ALPHA_8;

public class BitmapUtils {

    public static Bitmap generateRandomBitmap(int width, int height) {
        Bitmap result = Bitmap.createBitmap(width, height, ALPHA_8);
        Random random = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result.setPixel(x, y, random.nextInt());
            }
        }
        return result;
    }

    public static boolean areBitmapPixelsSame(@NonNull Bitmap a, @NonNull Bitmap b) {
        if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
            return false;
        }
        for (int x = 0; x < a.getWidth(); x++) {
            for (int y = 0; y < a.getHeight(); y++) {
                if (a.getPixel(x, y) != b.getPixel(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}
