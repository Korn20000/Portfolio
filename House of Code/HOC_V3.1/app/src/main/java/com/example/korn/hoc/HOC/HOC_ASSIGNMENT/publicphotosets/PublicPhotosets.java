
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.publicphotosets;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Getter and setter for public photo list
 *
 */

public class PublicPhotosets
{
    @Expose
    private List<PublicPhotos> photoset = new ArrayList<PublicPhotos>();

    public List<PublicPhotos> getPhotoset()
    {
        return photoset;
    }

    public void setPhotoset(List<PublicPhotos> photoset)
    {
        this.photoset = photoset;
    }

}
