package com.flickrsearch;

import com.flickrsearch.data.mapper.JsonMapper;
import com.flickrsearch.util.decoder.InputStreamDecoder;
import com.flickrsearch.util.decoder.JsonInputStreamDecoder;
import com.flickrsearch.util.network.Result;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class JsonInputStreamDecoderTest {

    private static class TestClass {
        private int i;
        private double d;
        private String s;
    }

    private static class TestClassMapper implements JsonMapper<TestClass> {
        @Override
        public TestClass map(String jsonString) throws JSONException {
            TestClass testClass = new TestClass();
            JSONObject jsonObject = new JSONObject(jsonString);
            testClass.i = jsonObject.getInt("i");
            testClass.d = jsonObject.getDouble("d");
            testClass.s = jsonObject.getString("s");
            return testClass;
        }
    }

    private InputStream toInputStream(String string) {
        return new ByteArrayInputStream(string.getBytes(Charset.forName("UTF-8")));
    }

    private Result<TestClass> decodeStream(InputStream inputStream) {
        JsonMapper<TestClass> jsonMapper = new TestClassMapper();
        InputStreamDecoder<TestClass> inputStreamDecoder = new JsonInputStreamDecoder<>(jsonMapper);
        return inputStreamDecoder.decodeStream(inputStream);
    }

    @Test
    public void testStreamDecoderIsCorrect() {
        int expectedI = 2;
        double expectedD = 3.14;
        String expectedS = "test";
        String jsonString = "{\"i\":" + expectedI + ",\"d\":" + expectedD + ",\"s\":\"" + expectedS + "\"}";

        InputStream inputStream = toInputStream(jsonString);
        Result<TestClass> result = decodeStream(inputStream);
        assertNotNull(result);
        assertNotNull(result.getValue());
        assertNull(result.getException());

        TestClass value = result.getValue();
        assertEquals(expectedI, value.i);
        assertEquals(expectedD, value.d, 0.001);
        assertEquals(expectedS, value.s);
    }

    @Test
    public void testStreamDecoderWithException() {
        InputStream inputStream = toInputStream("{}");
        Result<TestClass> result = decodeStream(inputStream);
        assertNotNull(result);
        assertNull(result.getValue());
        assertNotNull(result.getException());
    }

}
