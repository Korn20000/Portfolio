
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.publicphotosets;

import com.google.gson.annotations.Expose;


/**
 * Created by KoRn on 13-03-2018.
 *
 * Getter and setter for public photo list
 *
 */

public class Public_Photosets
{

    @Expose
    private PublicPhotosets photosets;

    public PublicPhotosets getPhotosets()
    {
        return photosets;
    }


    public void setPhotosets(PublicPhotosets photosets)
    {
        this.photosets = photosets;
    }


}
