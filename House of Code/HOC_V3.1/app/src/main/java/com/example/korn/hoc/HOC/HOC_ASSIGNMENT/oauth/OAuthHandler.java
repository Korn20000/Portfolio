package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.oauth;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.Lists;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.Constants;
import com.wuman.android.auth.oauth.OAuthHmacCredential;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;

import java.io.IOException;


/**
 * Created by KoRn on 11-03-2018.
 */


public class OAuthHandler
{
    protected static final String tag = "OAuthHandler";

    public static final String tempTokenReq = "http://m.flickr.com/services/oauth/request_token";
    public static final String verifierServer = "http://m.flickr.com/services/oauth/authorize";
    public static final String tokenServer = "http://m.flickr.com/services/oauth/access_token";
    public static final String redirectURL = "http://localhost/Callback";

    protected OAuth oAuth;
    // Keeping  the credential stored in the memory
    protected Credential credential;

    public Credential getCredential()
    {
        return credential;
    }

    // Start new request if no token is stored for OAuth
    public boolean getOAuthToken(FragmentActivity activity, final String userId)
    {
        boolean retVal = false;

        oAuth = OAuth.newInstance(activity.getApplicationContext(), activity.getSupportFragmentManager(),
                new ClientParametersAuthentication(Constants.apiKey, Constants.apiSecretKey), verifierServer,
                tokenServer, redirectURL, Lists.<String>newArrayList(), tempTokenReq);

        try
        {
            credential = oAuth.authorize10a(userId).getResult();

            if(Constants.debugTag)
            {
                Log.i(tag, "token: " + credential.getAccessToken());
            }

            if(credential instanceof OAuthHmacCredential)
            {
                if(Constants.debugTag)
                {
                    Log.i(tag, "tokenSecret: " + ((OAuthHmacCredential) credential).getTokenSharedSecret());
                }

                retVal = true;
            }

        }
        catch(IOException e)
        {
            Log.e(tag, e.getMessage(), e);
        }

        return retVal;
    }

    //Remove the stored values if they are not used, if there is no exception then continue
    public boolean removeCredential(final Context context, final String userId)
    {
        boolean status = false;

        SharedPreferencesCredentialStore credentialStore = new SharedPreferencesCredentialStore(context, OAuth.storeCredsFiles, OAuth.jsonFactory);

        try
        {
            credentialStore.delete(userId, credential);
            credential = null;
            status = true;
        }
        catch (IOException e)
        {
            if(Constants.debugTag)
            {
                Log.e(tag, "Exception deleting credential", e);
            }
        }

        return status;
    }


    // Load the credentials if it has been stored in previous sessions
    public boolean checkPreviousAuthorization(final Context context, final String userId)
    {
        boolean status = false;

        SharedPreferencesCredentialStore credentialStore = new SharedPreferencesCredentialStore(context, OAuth.storeCredsFiles, OAuth.jsonFactory);

        if(credential == null)
        {
            credential = new OAuthHmacCredential.Builder(BearerToken.authorizationHeaderAccessMethod(), "", "").build();
        }

        try
        {
            status = credentialStore.load(userId, credential);
        }
        catch (IOException e)
        {
            if(Constants.debugTag)
            {
                Log.e(tag, "Exception loading credential", e);
            }
        }
        return status;
    }
}
