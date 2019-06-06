package com.flickrsearch.presentation;

import android.content.Context;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.flickrsearch.Configuration;
import com.flickrsearch.R;
import com.flickrsearch.data.Photo;
import com.flickrsearch.util.image.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends BaseAdapter {

    private final Context context;
    private final NetworkInfo networkInfo;
    private final List<Photo> photos = new ArrayList<>();

    PhotoAdapter(@NonNull Context context, NetworkInfo networkInfo) {
        this.context = context;
        this.networkInfo = networkInfo;
    }

    void setPhotos(List<Photo> photos, boolean shouldClear) {
        if (shouldClear) {
            this.photos.clear();
        }
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_photo, null);
            ViewHolder viewHolder = new ViewHolder(convertView, networkInfo);
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bind(photos.get(position));

        return convertView;
    }

    private static class ViewHolder {
        private final ImageView imageView;
        private final NetworkInfo networkInfo;

        private ViewHolder(View root, NetworkInfo networkInfo) {
            this.imageView = root.findViewById(R.id.imageView);
            this.networkInfo = networkInfo;
        }

        private void bind(Photo photo) {
            String imageUrl = Configuration.createFlickrImageUrl(photo);
            ImageDownloader.download(networkInfo, imageUrl, imageView);
        }

    }
}
