
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.privatephotoset;

import com.google.gson.annotations.Expose;

/**
 * Created by KoRn on 13-03-2018.
 *
 *Getter and setter for show private photo-sets
 */


public class PrivatePhotoSet {

    @Expose
    private PrivatePhotoList photoset;
    @Expose
    private String stat;

    public PrivatePhotoList getPhotoset()
    {
        return photoset;
    }

    public void setPhotoset(PrivatePhotoList photoset)
    {
        this.photoset = photoset;
    }


    public String getStat()
    {
        return stat;
    }

    public void setStat(String stat)
    {
        this.stat = stat;
    }

}
