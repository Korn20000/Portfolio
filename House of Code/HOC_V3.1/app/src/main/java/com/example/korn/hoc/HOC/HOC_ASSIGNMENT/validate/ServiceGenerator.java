package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.validate;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.Constants;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.signing.SigningClientCheck;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import se.akerfeldt.signpost.retrofit.RetrofitHttpOAuthConsumer;

/**
 * Created by KoRn on 14-03-2018.
 *
 * A service that use previous retried OAuth token and secret keys****
 *
 */

public class ServiceGenerator
{
    public static final String flickrURL = "https://api.flickr.com";


    // ****
    public static <S> S createServiceSigned(Class<S> serviceClass, String baseUrl, final String token, final String tokenSecret)
    {
        RetrofitHttpOAuthConsumer oAuthConsumer = new RetrofitHttpOAuthConsumer(Constants.apiKey, Constants.apiSecretKey);
        oAuthConsumer.setTokenWithSecret(token, tokenSecret);

        OkClient client = new SigningClientCheck(oAuthConsumer);

        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(baseUrl).setClient(client);

        RestAdapter adapter = builder.build();

        if(Constants.debugTag)
        {
            adapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return adapter.create(serviceClass);
    }
}