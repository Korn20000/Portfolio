package com.example.korn.hoc.HOC.HOC_ASSIGNMENT;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.display.PhotoAdapter;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interfaces.PhotoCommon;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interstingphotos.PublicInterestingPics;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.privatephotoset.PrivatePhotoSet;
import com.example.korn.hoc.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Show private and public pictures
 *
 */
public class PhotosetViewer extends AppCompatActivity
{

    public static final String intentPhotosetID = "intent Photoset ID";
    public static final String intentUserID = "intent User ID";

    public static final String publicPhotosetID = "public User ID";
    public static final String publicUserID = "public User ID";
    protected static final String interestingPics = "Public Interesting Pics";
    protected static final int argPerPage = 100;

    protected String photosetId;
    protected String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoset_fragment);

        // Set up action bar
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        if(null != intent)
        {
            photosetId = intent.getStringExtra(intentPhotosetID);
            userId = intent.getStringExtra(intentUserID);

            if(publicPhotosetID.equals(photosetId) && publicUserID.equals(userId))
            {
                int page = 1; // TODO handle paging
                ClientAndBus.getFlickrClient().getInterestingness(Integer.toString(argPerPage), Integer.toString(page), interestingnessCallback);
            }
            else
            {
                ClientAndBus.getFlickrClient().getPhotoset(photosetId, userId, photosetCallback);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected retrofit.Callback photosetCallback = new retrofit.Callback()
    {
        @Override
        public void success(Object o, Response response)
        {
            if (Constants.debugTag)
            {
                Log.i("photoset Callback", "Received photoset");
            }

            PrivatePhotoSet photoset = (PrivatePhotoSet)o;


            ArrayList<PhotoCommon> list = new ArrayList<>();
            list.addAll(photoset.getPhotoset().getPhoto());
            showPhotos(list);
        }

        @Override
        public void failure(RetrofitError retrofitError)
        {
            if (Constants.debugTag)
            {
                Log.e("photoset Callback", "Retrofit Error" + retrofitError.getMessage());
            }
        }
    };

    protected retrofit.Callback interestingnessCallback = new retrofit.Callback()
    {
        @Override
        public void success(Object o, Response response)
        {
            getSupportActionBar().setTitle(interestingPics);

            PublicInterestingPics interestingness = (PublicInterestingPics)o;
            ArrayList<PhotoCommon> list = new ArrayList<>();
            list.addAll(interestingness.getPhotos().getPhoto());
            showPhotos(list);
        }

        @Override
        public void failure(RetrofitError retrofitError)
        {
            if (Constants.debugTag)
            {
                Log.e("loadPhotos Callback", "Retrofit Error" + retrofitError.getMessage());
            }
        }
    };


    protected void showPhotos(List<PhotoCommon> photos)
    {
        if(null == photos)
        {
            if (Constants.debugTag)
            {
                Log.w("show Photos", "Failed to show photos, null input");
            }
        }
        else
        {
            final GridView gridView = (GridView) findViewById(R.id.photosetGridView);

            gridView.setAdapter(new PhotoAdapter(this, photos));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    PhotoCommon photo = (PhotoCommon) gridView.getItemAtPosition(position);

                    final Intent intent = new Intent(PhotosetViewer.this, PhotoViewer.class);
                    intent.putExtra(PhotoViewer.intentPhotoURL, ImageUrl.getUrl(photo, ImageUrl.fullSize));
                    intent.putExtra(PhotoViewer.intentPhotoID, photo.getId());
                    intent.putExtra(PhotoViewer.intentPhotoSecretKey, photo.getSecret());
                    if(userId != null && userId.compareTo(ClientAndBus.getOwnUserId()) == 0)
                    {

                    }
                    startActivity(intent);

                }
            });
        }
    }
}
