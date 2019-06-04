package com.flickrsearch.data;

public class Photo {
    private final String id;
    private final String secret;
    private final String server;
    private final String farm;

    public Photo(String id, String secret, String server, String farm) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
    }

    @Override
    public String toString() {
        return Photo.class.getSimpleName()
                + " id<" + id
                + ">, secret<" + secret
                + ">, server<" + server
                + ">, farm<" + farm
                + ">";
    }
}
