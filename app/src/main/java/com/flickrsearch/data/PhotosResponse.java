package com.flickrsearch.data;

public class PhotosResponse {
    private final PhotosPage photosPage;

    public PhotosResponse(PhotosPage photosPage) {
        this.photosPage = photosPage;
    }

    public PhotosPage getPhotosPage() {
        return photosPage;
    }

    @Override
    public String toString() {
        return PhotosResponse.class.getSimpleName() + " photosPage<" + photosPage + ">";
    }
}
