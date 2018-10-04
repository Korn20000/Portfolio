package com.example.korn.hoc.HOC.HOC_ASSIGNMENT;

import android.app.Application;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interfaces.FlickrClient;
import com.squareup.otto.Bus;


/**
 * Created by KoRn on 14-03-2018.
 *
 * Fetching personal user-id, Flickr client and event bus
 *
 */
public class ClientAndBus extends Application
{
    protected static Bus bus;
    protected static FlickrClient flickrClient;
    protected static String OwnUserId;

    public static Bus getBusInstance()
    {
        return bus;
    }

    public static FlickrClient getFlickrClient()
    {
        return flickrClient;
    }

    public static void setFlickrClient(final FlickrClient client)
    {
        flickrClient = client;
    }

    public static void setOwnUserId(final String userId)
    {
        OwnUserId = userId;
    }

    public static String getOwnUserId()
    {
        return OwnUserId;
    }

    public final void onCreate()
    {
        super.onCreate();
        bus = new Bus();
    }
}