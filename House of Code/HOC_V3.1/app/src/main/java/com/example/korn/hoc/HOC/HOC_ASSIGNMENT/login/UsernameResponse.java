
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.login;

import com.google.gson.annotations.Expose;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Response getter and setter for retrieving from that specific account
 *
 */

public class UsernameResponse
{

    @Expose
    private User user;
    @Expose
    private String stat;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
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
