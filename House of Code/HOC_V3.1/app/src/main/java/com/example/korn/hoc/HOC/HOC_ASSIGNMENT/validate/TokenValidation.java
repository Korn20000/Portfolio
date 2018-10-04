
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.validate;

import com.google.gson.annotations.Expose;

/**
 * Created by KoRn on 13-03-2018.
 *
 * getter and setter for testing token while authorization
 *
 */

public class TokenValidation
{

    @Expose
    private ValidationUser user;
    @Expose
    private String stat;

    public ValidationUser getUser()
    {
        return user;
    }

    public void setUser(ValidationUser user)
    {
        this.user = user;
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
