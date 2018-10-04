package com.example.korn.hoc.HOC.HOC_ASSIGNMENT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.korn.hoc.R;
import com.squareup.picasso.Picasso;

/**
 * Created by KoRn on 10-03-2018.
 *
 * Show a single photo
 */
public class PhotoViewer extends Activity
{
    public static final String intentPhotoURL = "intent Photo URL";
    public static final String intentPhotoID = "intent Photo ID";
    public static final String intentPhotoSecretKey = "intent Photo Secret Key";

    protected String mPhotoId;
    protected String mPhotoSecret;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_viewer);

        final Intent intent = getIntent();

        if(null != intent)
        {
            final String photoUrl = intent.getStringExtra(intentPhotoURL);

            mPhotoId = intent.getStringExtra(intentPhotoID);
            mPhotoSecret = intent.getStringExtra(intentPhotoSecretKey);

            final ImageView imageView = findViewById(R.id.photo_viewer_image);

            if(null != photoUrl)
            {
                Picasso.with(this).load(photoUrl).placeholder(R.drawable.ic_loading_picture).error(R.drawable.ic_warning_picture).tag(this).into(imageView);
            }
        }
    }
}
