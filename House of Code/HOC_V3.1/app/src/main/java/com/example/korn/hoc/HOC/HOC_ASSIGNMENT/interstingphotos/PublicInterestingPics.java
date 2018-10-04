
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interstingphotos;

import com.google.gson.annotations.Expose;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Getter and setter for show public interesting pictures
 *
 */


public class PublicInterestingPics
{

    @Expose
    private Photos photos;

    public Photos getPhotos()
    {
        return photos;
    }

    public void setPhotos(Photos photos)
    {
        this.photos = photos;
    }
}
