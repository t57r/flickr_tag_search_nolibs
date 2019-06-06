package com.flickrsearch;

import com.flickrsearch.data.Photo;
import com.flickrsearch.data.PhotosPage;
import com.flickrsearch.data.PhotosResponse;
import com.flickrsearch.data.mapper.JsonMapper;
import com.flickrsearch.data.mapper.PhotosResponseMapper;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

public class PhotosResponseMapperTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testMapperIsCorrect() {
        long expectedPage = 1;
        long expectedPages = 1633;
        String expectedPhotoId = "47992620978";
        String expectedSecret = "16a7f5332b";
        String expectedServer = "65535";
        String expectedFarm = "66";

        String json = "{" +
                "\"photos\":{" +
                "\"page\":" + expectedPage + "," +
                "\"pages\":" + expectedPages + "," +
                "\"photo\":[{" +
                "\"id\":\"" + expectedPhotoId + "\"," +
                "\"secret\":\"" + expectedSecret + "\"," +
                "\"server\":\"" + expectedServer + "\"," +
                "\"farm\":" + expectedFarm +
                "}]" +
                "}" +
                "}";

        JsonMapper<PhotosResponse> jsonMapper = new PhotosResponseMapper();

        try {
            PhotosResponse photosResponse = jsonMapper.map(json);
            assertNotNull(photosResponse);

            PhotosPage photosPage = photosResponse.getPhotosPage();
            assertNotNull(photosPage);

            assertEquals(expectedPage, photosPage.getPage());
            assertEquals(expectedPages, photosPage.getPages());

            List<Photo> photos = photosPage.getPhotos();
            assertNotNull(photos);
            assertEquals(1, photos.size());

            Photo firstPhoto = photos.get(0);
            assertEquals(expectedPhotoId, firstPhoto.getId());
            assertEquals(expectedSecret, firstPhoto.getSecret());
            assertEquals(expectedServer, firstPhoto.getServer());
            assertEquals(expectedFarm, firstPhoto.getFarm());

        } catch (JSONException e) {
            fail("Exception is not expected");
        }
    }

    @Test
    public void testMapperWithException() {
        String badJson = "{}";
        JsonMapper<PhotosResponse> jsonMapper = new PhotosResponseMapper();
        try {
            jsonMapper.map(badJson);
            exception.expect(JSONException.class);
        } catch (JSONException e) {
        }
    }

}
