
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.privatephotoset;

import com.google.gson.annotations.Expose;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interfaces.PhotoCommon;

/**
 * Created by KoRn on 13-03-2018.
 *
 *
 * Getter and setter for show one private picture
 *
 *
 */

public class PrivatePhotos implements PhotoCommon {

    @Expose
    private String id;
    @Expose
    private String secret;
    @Expose
    private String server;
    @Expose
    private int farm;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    public int getFarm()
    {
        return farm;
    }

    public void setFarm(int farm)
    {
        this.farm = farm;
    }

}
