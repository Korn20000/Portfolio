
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interstingphotos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Getter and setter for show public multiple pictures
 *
 */


public class Photos
{
    private List<Photo> photo = new ArrayList<Photo>();

    public List<Photo> getPhoto()
    {
        return photo;
    }

    public void setPhoto(List<Photo> photo)
    {
        this.photo = photo;
    }
}
