package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.display;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.ImageUrl;
import com.example.korn.hoc.R;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.publicphotosets.PublicPhotos;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.publicphotosets.Public_Photosets;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class PhotosetAdapter extends BaseAdapter
{
    protected final Context context;

    protected final List<PublicPhotos> myPhotosets;

    public PhotosetAdapter(Context context, final Public_Photosets photosets)
    {
        this.context = context;

        myPhotosets = photosets.getPhotosets().getPhotoset();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent)
    {
        PublicPhotos photoset = myPhotosets.get(position);

        final String url = ImageUrl.getUrl(photoset);

        ImageSquare view = (ImageSquare) convertView;
        if (view == null)
        {
            view = new ImageSquare(context);
            view.setScaleType(CENTER_CROP);
        }

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context).load(url).placeholder(R.drawable.ic_loading_picture).error(R.drawable.ic_warning_picture).fit().tag(context).into(view);

        return view;
    }

    @Override public int getCount() {
        return myPhotosets.size();
    }

    @Override public PublicPhotos getItem(int position) {
     return myPhotosets.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }
}