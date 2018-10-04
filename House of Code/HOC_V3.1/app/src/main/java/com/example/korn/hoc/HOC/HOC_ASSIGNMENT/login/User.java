
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.login;

import com.google.gson.annotations.Expose;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Getter and setter for login user
 *
 */

public class User
{

    @Expose
    private String id;
    @Expose
    private String nsid;
    @Expose
    private Username username;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNsid()
    {
        return nsid;
    }

    public void setNsid(String nsid)
    {
        this.nsid = nsid;
    }

    public Username getUsername()
    {
        return username;
    }

    public void setUsername(Username username)
    {
        this.username = username;
    }

}
