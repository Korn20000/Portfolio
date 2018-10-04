
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.userinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Set users content for user description
 *
 */


public class UserDescription
{

    @SerializedName("_content")
    @Expose
    private String Content;

    public String getContent()
    {
        return Content;
    }

    public void setContent(String Content)
    {
        this.Content = Content;
    }

}
