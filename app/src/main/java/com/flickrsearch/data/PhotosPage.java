package com.flickrsearch.data;

import java.util.List;

public class PhotosPage {
    private final long page;
    private final long pages;
    private final long perpage;
    private final String total;
    private final List<Photo> photos;

    public PhotosPage(long page, long pages, long perpage, String total, List<Photo> photos) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    @Override
    public String toString() {
        return PhotosPage.class.getSimpleName()
                + " page<" + page
                + ">, pages<" + pages
                + ">, perpage<" + perpage
                + ">, total<" + total
                + ">, photos<" + photos
                + ">";
    }
}
