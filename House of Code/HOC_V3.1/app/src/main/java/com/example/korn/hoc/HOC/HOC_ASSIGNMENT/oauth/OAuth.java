package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.oauth;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.wuman.android.auth.AuthorizationDialogController;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;
import com.wuman.android.auth.OAuthManager.OAuthCallback;
import com.wuman.android.auth.OAuthManager.OAuthFuture;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;

import java.io.IOException;
import java.util.List;

/*
 *  Created by KoRn on 11-03-2018.
 * This class creates a workflow in the OAuth, stores the credentials and sets
 * a temporary token request
 *
 */
public class OAuth
{
    public static final String storeCredsFiles = "oauth";

    public static final JsonFactory jsonFactory = new JacksonFactory();
    public static final HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();

    protected static final boolean fullscreen = true;

    private final OAuthManager manager;

    // Creating a workflow for OAuth
    public static OAuth newInstance(Context context, FragmentManager fragmentManager, ClientParametersAuthentication client, String authorizationRequestUrl,
                                    String tokenServerUrl, final String redirectUri, List<String> scopes, String temporaryTokenRequestUrl)
    {
        Preconditions.checkNotNull(client.getClientId());

        // The credentials are stored
        SharedPreferencesCredentialStore credentialStore = new SharedPreferencesCredentialStore(context, storeCredsFiles, jsonFactory);
        // An authorized workflow is being established
        AuthorizationFlow.Builder flowBuilder = new AuthorizationFlow.Builder(BearerToken.authorizationHeaderAccessMethod(), httpTransport,
                jsonFactory, new GenericUrl(tokenServerUrl), client, client.getClientId(), authorizationRequestUrl).setScopes(scopes).setCredentialStore(credentialStore);
        // A temporary token is being requested for the workflow
        if (!TextUtils.isEmpty(temporaryTokenRequestUrl))
        {
            flowBuilder.setTemporaryTokenRequestUrl(temporaryTokenRequestUrl);
        }
        AuthorizationFlow flow = flowBuilder.build();
        // ValidationUser Interface for the Authorization
        AuthorizationDialogController controller = new DialogFragmentController(fragmentManager, fullscreen)
        {
            @Override
            public String getRedirectUri() throws IOException
            {
                return redirectUri;
            }

            @Override
            public boolean isJavascriptEnabledForWebView()
            {
                return true;
            }

            @Override
            public boolean disableWebViewCache()
            {
                return false;
            }

            @Override
            public boolean removePreviousCookie()
            {
                return false;
            }
        };

        return new OAuth(flow, controller);
    }

    private OAuth(AuthorizationFlow flow, AuthorizationDialogController controller)
    {
        Preconditions.checkNotNull(flow);
        Preconditions.checkNotNull(controller);
        this.manager = new OAuthManager(flow, controller);
    }

    public OAuthFuture<Boolean> deleteCredential(String userId)
    {
        return deleteCredential(userId, null);
    }

    public OAuthFuture<Boolean> deleteCredential(String userId, OAuthCallback<Boolean> callback)
    {
        return deleteCredential(userId, callback, null);
    }

    public OAuthFuture<Boolean> deleteCredential(String userId, OAuthCallback<Boolean> callback,
            Handler handler)
    {
        return manager.deleteCredential(userId, callback, handler);
    }


     // A callback handler for authorization

    public OAuthFuture<Credential> authorize10a(String userId)
    {
        return authorize10a(userId, null);
    }

    public OAuthFuture<Credential> authorize10a(String userId, OAuthCallback<Credential> callback)
    {
        return authorize10a(userId, callback, null);
    }

    public OAuthFuture<Credential> authorize10a(String userId, OAuthCallback<Credential> callback, Handler handler)
    {
        return manager.authorize10a(userId, callback, handler);
    }
}