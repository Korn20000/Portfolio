package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.display;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.ImageUrl;
import com.example.korn.hoc.R;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interfaces.PhotoCommon;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by KoRn on 11-03-2018.
 */

public class PhotoAdapter extends BaseAdapter
{
    protected final Context context;

    protected final List<PhotoCommon> myPhotos;

    public PhotoAdapter(Context context, final List<PhotoCommon> photos)
    {
        this.context = context;

        myPhotos = photos;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent)
    {
        PhotoCommon photo = myPhotos.get(position);

        final String url = ImageUrl.getUrl(photo, ImageUrl.thumbnail);

        ImageSquare view = (ImageSquare) convertView;
        if (view == null)
        {
            view = new ImageSquare(context);
            view.setScaleType(CENTER_CROP);
        }

        // "Downloads" the URL asynchronously for the ImageView.
        Picasso.with(context).load(url).placeholder(R.drawable.ic_loading_picture).error(R.drawable.ic_warning_picture).fit().tag(context).into(view);

        return view;
    }

    @Override public int getCount()
    {
        return myPhotos.size();
    }

    @Override public PhotoCommon getItem(int position)
    {
        return myPhotos.get(position);
    }

    @Override public long getItemId(int position)
    {
        return position;
    }
}