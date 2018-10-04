package com.example.korn.hoc.HOC.HOC_ASSIGNMENT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.display.PhotosetAdapter;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.events.UpdateRequest;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interfaces.FlickrClient;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.publicphotosets.PublicPhotos;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.publicphotosets.Public_Photosets;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.userinfo.UserPerson;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.userinfo.UserData;
import com.example.korn.hoc.R;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *
 * Created by KoRn on 10-03-2018.
 *
 * Show the user's data, photos, etc.
 *
 */
public class UserFragment extends Fragment
{
    public static final String fragTag = "UserFragment";

    protected UserData userData;
    protected Public_Photosets public_photosets;
    protected String userId;

    public static String getTitle(final Context context)
    {
        return context.getString(R.string.fragment_title_user_data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState)
    {
        final View userView = getActivity().getLayoutInflater().inflate(R.layout.user_fragment, null);

        final View infoButton = userView.findViewById(R.id.user_bar_info);

        infoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickShowUserInfo(v);
            }
        });

        return userView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ClientAndBus.getBusInstance().register(this);

        // prompt the parent activity to send data if there is nothing
        if(null == userId)
        {
            ClientAndBus.getBusInstance().post(new UpdateRequest());
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        ClientAndBus.getBusInstance().unregister(this);
    }

    @Subscribe
    public void UserIdUpdateEvent(MainMenu.UserIdUpdate event)
    {
        final String userId = event.getUserId();

        if (Constants.debugTag)
        {
            Log.i("User Id Update Event", "Received event for ID " + userId);
        }

        // Skip reloading if userId is the same
        if(null == this.userId || this.userId.compareTo(userId) != 0)
        {
            setUserId(userId);
        }
    }

    public void updateData(final UserData userData)
    {
        final View view = getView();
        final UserPerson user = userData.getPerson();

        if(user == null)
        {
            if (Constants.debugTag)
            {
                Log.i(fragTag, "No user in UserData response");
            }
        }
        else
        {
            final String userId = user.getNsid();
            final String userName = user.getUsername().getContent();
            final String iconUrl = ImageUrl.getProfilPicUrl(userId);

            if (Constants.debugTag)
            {
                Log.i(fragTag, "Getting data for NSID " + userId + " with URL " + iconUrl);
            }

            final ImageView profileIconView = (ImageView) view.findViewById(R.id.user_bar_icon);
            final TextView profileNameView = (TextView) view.findViewById(R.id.user_bar_name);

            Picasso.with(getActivity()).load(iconUrl).into(profileIconView);
            profileNameView.setText(userName);
        }
    }

    public void onClickShowUserInfo(final View v)
    {
        if(null != userData)
        {
            final UserPerson user = userData.getPerson();

            // Assemble user data for display
            SpannableStringBuilder builder = new SpannableStringBuilder();

            StaticUtil.addPairToSpannable(builder, "Real Name", user.getRealname().getContent());
            StaticUtil.addPairToSpannable(builder, "Validation User Name", user.getUsername().getContent());
            StaticUtil.addPairToSpannable(builder, "Profile URL", user.getProfileurl().getContent());
            StaticUtil.addPairToSpannable(builder, "User Description", user.getDescription().getContent());

            StaticUtil.showOkAlert(getActivity(), new SpannableString(builder));
        }
    }

    protected void showPhotosets(Public_Photosets photosets)
    {
        if(null == photosets)
        {
            if (Constants.debugTag)
            {
                Log.w("show Photosets", "Failed to show photosets, null input");
            }
        }
        else
        {
            final GridView gridView = (GridView) getActivity().findViewById(R.id.userFragment).findViewById(R.id.photosetGridView);

            gridView.setAdapter(new PhotosetAdapter(getActivity(), photosets));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    // Now, show the photoset
                    PublicPhotos photoset = (PublicPhotos) gridView.getItemAtPosition(position);

                    if (Constants.debugTag)
                    {
                        Log.i("show Photosets", "Clicked on photoset id " + photoset.getId());
                    }

                    final Intent intent = new Intent(getActivity(), PhotosetViewer.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(PhotosetViewer.intentPhotosetID, photoset.getId());
                    intent.putExtra(PhotosetViewer.intentUserID, userId);
                    startActivity(intent);
                }
            });
        }
    }


    protected retrofit.Callback loadPhotosetsCallback = new retrofit.Callback()
    {
        @Override
        public void success(Object o, Response response)
        {
            showPhotosets((Public_Photosets) o);
        }

        @Override
        public void failure(RetrofitError retrofitError)
        {
            if (Constants.debugTag)
            {
                Log.e("load Photosets Callback", "Retrofit Error" + retrofitError.getMessage());
            }
        }
    };

    protected retrofit.Callback loadUserDataCallback = new retrofit.Callback()
    {
        @Override
        public void success(Object o, Response response)
        {
            userData = (UserData)o;
            updateData(userData);
        }

        @Override
        public void failure(RetrofitError retrofitError)
        {
            if (Constants.debugTag)
            {
                Log.e("load User Data Callback", "Retrofit Error" + retrofitError.getMessage());
            }
        }
    };

    public void setUserId(final String userId)
    {
        this.userId = userId;

        FlickrClient flickerClient = ((MainMenu) getActivity()).getFlickrClient();

        if (Constants.debugTag)
        {
            Log.i(fragTag, "Now loading data for userId " + userId);
        }

        if (null != flickerClient)
        {
            // Initiate the image set loading with callback
            flickerClient.getPhotosetList(userId, loadPhotosetsCallback);

            // Initiate user data loading with callback
            flickerClient.getUserData(userId, loadUserDataCallback);
        } else if (Constants.debugTag)
        {
            Log.w("on Click Load", "Failed to load data, Flicker Client is null");
            userData = null;
            public_photosets = null;
        }
    }
}
