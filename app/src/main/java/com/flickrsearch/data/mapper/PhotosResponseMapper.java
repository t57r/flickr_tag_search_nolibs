package com.flickrsearch.data.mapper;

import com.flickrsearch.data.Photo;
import com.flickrsearch.data.PhotosPage;
import com.flickrsearch.data.PhotosResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhotosResponseMapper implements JsonMapper<PhotosResponse> {

    /**
     Converts jsonString to PhotosResponse
     {
        "photos":{
            "page":1,
            "pages":1633,
            "photo":[
            {
                "id":"47992620978",
                "secret":"16a7f5332b",
                "server":"65535",
                "farm":66
            }]
        }
     }
    */
    @Override
    public PhotosResponse map(String jsonString) throws JSONException {
        JSONObject root = new JSONObject(jsonString);
        JSONObject photosObj = root.getJSONObject("photos");
        JSONArray photosArray = photosObj.getJSONArray("photo");
        List<Photo> photoList = new ArrayList<>();
        for (int i = 0; i < photosArray.length(); i++) {
            JSONObject arrayPhoto = photosArray.getJSONObject(i);
            photoList.add(new Photo(
                    arrayPhoto.getString("id"),
                    arrayPhoto.getString("secret"),
                    arrayPhoto.getString("server"),
                    arrayPhoto.getString("farm")
            ));
        }
        return new PhotosResponse(new PhotosPage(
                photosObj.getLong("page"),
                photosObj.getLong("pages"),
                photoList
        ));
    }
}
