package com.flickrsearch;

import android.graphics.Bitmap;

import com.flickrsearch.util.BitmapUtils;
import com.flickrsearch.util.decoder.BitmapInputStreamDecoder;
import com.flickrsearch.util.decoder.InputStreamDecoder;
import com.flickrsearch.util.network.Result;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class BitmapInputStreamDecoderTest {

    @Test
    public void testStreamDecoderIsCorrect() {
        Bitmap expectedBitmap = BitmapUtils.generateRandomBitmap(50, 50);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        expectedBitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        byte[] bitmapBytes = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bitmapBytes);

        InputStreamDecoder<Bitmap> streamDecoder = new BitmapInputStreamDecoder();
        Result<Bitmap> result = streamDecoder.decodeStream(inputStream);
        assertNotNull(result);
        assertNotNull(result.getValue());
        assertNull(result.getException());

        Bitmap decodedBitmap = result.getValue();
        assertEquals(expectedBitmap.getHeight(), decodedBitmap.getHeight());
        assertEquals(expectedBitmap.getWidth(), decodedBitmap.getWidth());

        assertTrue(BitmapUtils.areBitmapPixelsSame(expectedBitmap, decodedBitmap));
    }

    @Test
    public void testStreamDecoderWithException() {
        InputStream inputStream = new ByteArrayInputStream(new byte[] { 0 });
        InputStreamDecoder<Bitmap> streamDecoder = new BitmapInputStreamDecoder();
        Result<Bitmap> result = streamDecoder.decodeStream(inputStream);
        assertNotNull(result);
        assertNull(result.getValue());
        assertNotNull(result.getException());
    }


}
