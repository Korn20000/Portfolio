package com.example.korn.hoc.HOC.HOC_ASSIGNMENT;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interfaces.PhotoCommon;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.publicphotosets.PublicPhotos;

/*
 * Created by KoRn on 11-03-2018.
 *
 * Helper class to generate the download URL for the selected photo aka. caching
 *
 */
public class ImageUrl
{
    public static final boolean thumbnail = true;
    public static final boolean fullSize = false;

    protected static final String smallSize = "q";
    protected static final String medSize = "b";

    protected static final String urlFormat = "https://farm%d.staticflickr.com/%s/%s_%s_%s.jpg";

    protected static final String prefixProfilePic = "https://flickr.com/buddyicons/";
    protected static final String postfixProfilePic = ".jpg";


    public static String getUrl(final PhotoCommon photo, final boolean isThumbnail)
    {
        return getUrl(  photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret(), isThumbnail);
    }

    // Private Photo Set, which shows primary image
    public static String getUrl(final PublicPhotos photoset)
    {
        return getUrl(  photoset.getFarm(), photoset.getServer(), photoset.getPrimary(), photoset.getSecret(), thumbnail);
    }

    public static String getUrl(final int farm, final String server, final String id, final String secret, final boolean isThumbnail)
    {
        String size;

        if(isThumbnail)
        {
            size = smallSize;
        }
        else
        {
            size = medSize; // TBR
        }

        final String url = String.format(urlFormat, farm, server, id, secret, size);

        return url;
    }

    public static String getProfilPicUrl(final String userId)
    {
        return prefixProfilePic + userId + postfixProfilePic;
    }
}