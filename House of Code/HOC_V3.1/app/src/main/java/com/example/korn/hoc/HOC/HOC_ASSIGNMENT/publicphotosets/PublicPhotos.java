
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.publicphotosets;

import com.google.gson.annotations.Expose;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Getter and setter for public photos' first place/primary
 *
 */
public class PublicPhotos
{

    @Expose
    private String id;
    @Expose
    private String primary;
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

    public String getPrimary()
    {
        return primary;
    }

    public void setPrimary(String primary)
    {
        this.primary = primary;
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
