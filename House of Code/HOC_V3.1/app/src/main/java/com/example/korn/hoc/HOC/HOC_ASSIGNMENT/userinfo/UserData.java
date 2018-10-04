
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.userinfo;

import com.google.gson.annotations.Expose;

/**
 * Created by KoRn on 13-03-2018.
 *
 * getter and Setter to update userdata
 *
 */

public class UserData
{

    @Expose
    private UserPerson person;
    @Expose
    private String stat;

    public UserPerson getPerson()
    {
        return person;
    }

    public void setPerson(UserPerson person)
    {
        this.person = person;
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
