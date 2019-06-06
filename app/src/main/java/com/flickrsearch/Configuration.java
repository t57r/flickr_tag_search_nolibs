package com.flickrsearch;

import com.flickrsearch.data.Photo;

public class Configuration {

    private static final String API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736";

    public static String createFlickrGetPhotosUrl(String queryText, long pageNum) {
        return "https://api.flickr.com/services/rest/?method=flickr.photos.search"
                + "&api_key=" + API_KEY
                + "&format=json&nojsoncallback=1&safe_search=1"
                + "&text=" + queryText
                + "&page=" + pageNum;
    }

    public static String createFlickrImageUrl(Photo photo) {
        return "https://farm" + photo.getFarm() + ".static.flickr.com/" + photo.getServer()
                + "/" + photo.getId() + "_" + photo.getSecret() + ".jpg";
    }

}
