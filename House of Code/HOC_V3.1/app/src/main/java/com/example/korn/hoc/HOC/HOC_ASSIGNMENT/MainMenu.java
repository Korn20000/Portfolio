package com.example.korn.hoc.HOC.HOC_ASSIGNMENT;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.korn.hoc.R;
import com.google.api.client.auth.oauth2.Credential;
import com.example.korn.hoc.HOC.apache.FragTabPagerCompat;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.oauth.OAuthHandler;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interfaces.FlickrClient;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.validate.ServiceGenerator;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.validate.TokenValidation;
import com.squareup.otto.Bus;
import com.wuman.android.auth.oauth.OAuthHmacCredential;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *
 * Created by KoRn on 9-03-2018.
 *
 * Main activity for the assignment
 *
 */
public class MainMenu extends FragTabPagerCompat
{
    protected final static String tag = "Main Menu";

    protected OAuthHandler oAuthHandler;
    protected FlickrClient flickrClient;
    protected Bus bus;
    protected Dialog dialog;
    protected String userID;

    /*
     * Implement required abstact methods from FragTabPager
     */
    protected int getLayoutID()
    {
        return R.layout.frag_tab;
    }

    protected int getTabHostID()
    {
        return android.R.id.tabhost;
    }

    protected int getPagerID()
    {
        return R.id.pager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        // start shared event bus
        bus = ClientAndBus.getBusInstance();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setHomeButtonEnabled(true);

        // Load all tabs
        screenTabsAdapter.addTab(screenTabs.newTabSpec(UserFragment.fragTag).setIndicator(UserFragment.getTitle(this)), UserFragment.class, null);

        // Start data and authorization setup
        oAuthHandler = new OAuthHandler();

        /*
         * Check if already authorized (saved OAuth token)
         * if it is create flickr client to communicate with Flickr, then start the data initialization
         * if not, prompt user to log in
         */
        if (checkAndInitialize())
        {
            if (Constants.debugTag)
            {
                Log.i(tag, "checkAndInitialize done, now retrieving userId");
            }

            // If ownUser and ownUserId are not null, reload and don't init
            if(null == ClientAndBus.getOwnUserId() || null == userID)
            {
                // Find ID of logged in user
                flickrClient.testToken(validateTokenCallback);
            }
        }
    }

    @Override
    public void onDestroy()
    {
        if(null != dialog && dialog.isShowing())
        {
            dialog.dismiss();
        }

        super.onDestroy();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        bus.register(this);
    }

    @Override
    public void onPause()
    {
        bus.unregister(this);

        super.onPause();
    }

    //Show menu tab
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    //Make menu item clickable
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        boolean retVal;

        int id = item.getItemId();

        if (id == R.id.user_bar_info)
        {
            onActionUser();
            retVal = true;
        }
        else if (id == R.id.public_pics)
        {
            onActionFlickr();
            retVal = true;
        }
        else if (id == R.id.g_maps)
        {
            onActionMap();
            retVal = true;
        }
        else if (id == R.id.button_logout)
        {
            onActionLoginLogout();
            retVal = true;
        }
        else
        {
            retVal = super.onOptionsItemSelected(item);
        }
        return retVal;
    }

    // Load our own data
    protected void onActionUser()
    {
        if(null != ClientAndBus.getOwnUserId())
        {
            selectUserId(ClientAndBus.getOwnUserId());
        }
    }

    // Load interesting public pictures
    protected void onActionFlickr()
    {
        final Intent intent = new Intent(this, PhotosetViewer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PhotosetViewer.intentPhotosetID, PhotosetViewer.publicPhotosetID);
        intent.putExtra(PhotosetViewer.intentUserID, PhotosetViewer.publicUserID);
        startActivity(intent);
    }

    protected void onActionMap()
    {
        final Intent intent = new Intent(this, Maps.class);
        startActivity(intent);
    }

    /*
     * Log in or log out, depending on current state.
     */
    protected void onActionLoginLogout()
    {
        if(flickrClient == null)
        {
            OauthLoginTask task = new OauthLoginTask();
            task.execute();
        }
        else
        {
            oAuthLogout();
        }
    }

    /*
     * Check if OAuth token is stored if it is then load and initialize flickr client
     */
    protected boolean checkAndInitialize()
    {
        boolean result = oAuthHandler.checkPreviousAuthorization(this, Constants.singleUserId);

        if(Constants.debugTag)
        {
            Log.i(tag, "check for OAuth token: " + result);
        }

        if (result)
        {
            if(!initializeFlickrClient())
            {
                result = false;
                if(Constants.debugTag)
                {
                    Log.w(tag, "Failed to initialize FlickrClient");
                }
            }
        }
        return result;
    }


    /*
     * reuse credentials if they already exists or successfully created
     */
    protected boolean initializeFlickrClient()
    {
        if(null == flickrClient)
        {
            Credential credential = oAuthHandler.getCredential();

            if (credential instanceof OAuthHmacCredential)
            {
                OAuthHmacCredential hmacCredential = (OAuthHmacCredential) credential;

                flickrClient = ServiceGenerator.createServiceSigned(FlickrClient.class, ServiceGenerator.flickrURL,
                        hmacCredential.getAccessToken(), hmacCredential.getTokenSharedSecret());

                ClientAndBus.setFlickrClient(flickrClient);
            }
            else
            {
                if (Constants.debugTag)
                {
                    Log.w(tag, "Incorrect credential type, not able to get token secret");
                }
            }
        }
        return (null != flickrClient);
    }


    // Show login screen
    private class OauthLoginTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            boolean success = false;
            try
            {
                success = oAuthHandler.getOAuthToken(MainMenu.this, Constants.singleUserId);
            }
            catch (java.util.concurrent.CancellationException e)
            {
                if(Constants.debugTag)
                {
                    Log.w(tag, "Failed to get OAuth token due to user cancellation, exiting");
                }
                MainMenu.this.finish();
            }
            if(success)
            {
                if(initializeFlickrClient())
                {
                    // Is it the correct fetched ID of logged in user
                    flickrClient.testToken(validateTokenCallback);
                }
            }
            else
            {
                if(Constants.debugTag)
                {
                    Log.w(tag, "Failed to get FlickrClient due to failure to get OAuth token");
                }
            }
            return Boolean.toString(success);
        }
    }


    public FlickrClient getFlickrClient()
    {
        return flickrClient;
    }

    protected void oAuthLogout()
    {
        boolean success = oAuthHandler.removeCredential(MainMenu.this, Constants.singleUserId);

        if(Constants.debugTag)
        {
            Log.w(tag, "oAuthLogout: " + success);
        }

        // After logout, login again
        dialog = onPromptLogin();
    }

    protected void selectUserId(final String userId)
    {
        if(Constants.debugTag)
        {
            Log.w(tag, "selectUserId starting");
        }

        userID = userId;

        // Reload action bar items, in case of user switch
        invalidateOptionsMenu();

        // received by UserFragment
        bus.post(new UserIdUpdate(userId));
    }

    public class UserIdUpdate
    {
        protected final String userID;

        public UserIdUpdate(final String userId)
        {
            userID = userId;
        }

        public String getUserId()
        {
            return userID;
        }
    }

    //Diag. box tells you what to do
    public Dialog onPromptLogin()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(this.getString(R.string.login_title));
        alert.setMessage(this.getString(R.string.login_message));

        alert.setPositiveButton(this.getString(android.R.string.ok),
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        OauthLoginTask task = new OauthLoginTask();
                        task.execute();
                    }
                });

        alert.setNegativeButton(this.getString(android.R.string.cancel),
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        MainMenu.this.finish(); // can't continue without login
                    }
                });

        return alert.show();
    }

    //validate the token with a callback
    protected retrofit.Callback validateTokenCallback = new retrofit.Callback()
    {
        @Override
        public void success(Object o, Response response)
        {
            final TokenValidation tokenTest = (TokenValidation)o;
            final String userId = tokenTest.getUser().getId();

            if (Constants.debugTag)
            {
                Log.i("validate Token Callback", "ID = " + userId);
            }

            ClientAndBus.setOwnUserId(userId);
            selectUserId(userId);
        }

        @Override
        public void failure(RetrofitError retrofitError)
        {
            if (Constants.debugTag)
            {
                Log.e("validate Token Callback", "Retrofit Error" + retrofitError.getMessage());
            }
        }
    };
}