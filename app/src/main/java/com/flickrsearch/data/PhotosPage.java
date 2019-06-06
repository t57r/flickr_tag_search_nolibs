package com.flickrsearch.data;

import java.util.List;

public class PhotosPage {
    private final long page;
    private final long pages;
    private final List<Photo> photos;

    public PhotosPage(long page, long pages, List<Photo> photos) {
        this.page = page;
        this.pages = pages;
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public long getPage() {
        return page;
    }

    public long getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return PhotosPage.class.getSimpleName()
                + " page<" + page
                + ">, pages<" + pages
                + ">, photos<" + photos
                + ">";
    }
}
