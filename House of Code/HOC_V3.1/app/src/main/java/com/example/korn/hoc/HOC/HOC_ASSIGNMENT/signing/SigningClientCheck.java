package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.signing;

import android.util.Log;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.Constants;

import java.io.IOException;

import retrofit.client.Request;
import retrofit.client.Response;
import se.akerfeldt.signpost.retrofit.RetrofitHttpOAuthConsumer;
import se.akerfeldt.signpost.retrofit.SigningOkClient;

/**
 *  Created by KoRn on 14-03-2018.
 *
 * Adds the api signature and calculates MD5 hash for the URL
 *
 */
public class SigningClientCheck extends SigningOkClient
{
    protected final String secretToken;

    public SigningClientCheck(RetrofitHttpOAuthConsumer consumer)
    {
        super(consumer);
        secretToken = consumer.getTokenSecret();
    }

    /*
     *  Intercept the request before it is sent to modify the URL
     */
    @Override
    public Response execute(Request request) throws IOException
    {
        // Finalize the URL with static parameters
        String modifiedUrl = RequestSignin.addStaticParams(request.getUrl());
        //update the URL with the Flickr signature (MD5 hash)
        modifiedUrl = RequestSignin.signRequest(modifiedUrl, secretToken);
        final Request modifiedRequest = new Request(request.getMethod(), modifiedUrl, request.getHeaders(), request.getBody());

        if (Constants.debugTag)
        {
            Log.i("SigningClientCheck", "final signed request: " + modifiedUrl);
        }

        return super.execute(modifiedRequest);
    }
}
