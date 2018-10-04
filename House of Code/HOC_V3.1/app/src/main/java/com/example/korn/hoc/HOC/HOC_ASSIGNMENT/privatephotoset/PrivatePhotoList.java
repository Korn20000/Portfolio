
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.privatephotoset;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Getter and setter for private photo list
 *
 */


public class PrivatePhotoList
{

    @Expose
    private String id;

    private List<PrivatePhotos> photo = new ArrayList<PrivatePhotos>();

    public List<PrivatePhotos> getPhoto()
    {
        return photo;
    }

    public void setPhoto(List<PrivatePhotos> photo)
    {
        this.photo = photo;
    }

}
