package com.flickrsearch.data.mapper;

import org.json.JSONException;

public interface JsonMapper<T> {
    T map(String jsonString) throws JSONException;
}
