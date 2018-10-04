
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.userinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Retrieve and set data for userinfo
 *
 */

public class UserPerson
{

    @Expose
    private String id;
    @Expose
    private String nsid;
    private int iconserver;
    @Expose
    private int iconfarm;
    @SerializedName("path_alias")
    @Expose
    private String pathAlias;
    @SerializedName("has_stats")
    @Expose
    private int hasStats;
    @Expose
    private Username username;
    @Expose
    private UserRealName realname;
    @Expose
    private UserDescription description;
    @Expose
    private UserProfileUrl profileurl;

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


    public int getIconserver()
    {
        return iconserver;
    }

    public void setIconserver(int iconserver)
    {
        this.iconserver = iconserver;
    }

    public int getIconfarm()
    {
        return iconfarm;
    }

    public void setIconfarm(int iconfarm)
    {
        this.iconfarm = iconfarm;
    }

    public String getPathAlias()
    {
        return pathAlias;
    }

    public void setPathAlias(String pathAlias)
    {
        this.pathAlias = pathAlias;
    }

    public int getHasStats()
    {
        return hasStats;
    }

    public void setHasStats(int hasStats)
    {
        this.hasStats = hasStats;
    }

    public Username getUsername()
    {
        return username;
    }

    public void setUsername(Username username)
    {
        this.username = username;
    }

    public UserRealName getRealname()
    {
        return realname;
    }

    public void setRealname(UserRealName realname)
    {
        this.realname = realname;
    }

    public UserDescription getDescription()
    {
        return description;
    }

    public void setDescription(UserDescription description)
    {
        this.description = description;
    }

    public UserProfileUrl getProfileurl()
    {
        return profileurl;
    }

    public void setProfileurl(UserProfileUrl profileurl)
    {
        this.profileurl = profileurl;
    }

}
