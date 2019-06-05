package com.flickrsearch;

import com.flickrsearch.data.Photo;

public class Configuration {

    public static String createFlickrImageUrl(Photo photo) {
        return "https://farm" + photo.getFarm() + ".static.flickr.com/" + photo.getServer()
                + "/" + photo.getId() + "_" + photo.getSecret() + ".jpg";
    }

}
