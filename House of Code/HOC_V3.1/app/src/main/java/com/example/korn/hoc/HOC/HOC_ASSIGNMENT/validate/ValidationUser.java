
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.validate;

import com.google.gson.annotations.Expose;

/**
 * Created by KoRn on 13-03-2018.
 *
 * Validation for user
 *
 */

public class ValidationUser
{

    @Expose
    private String id;
    @Expose
    private UsernameValidation username;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public UsernameValidation getUsername()
    {
        return username;
    }

    public void setUsername(UsernameValidation username)
    {
        this.username = username;
    }

}
