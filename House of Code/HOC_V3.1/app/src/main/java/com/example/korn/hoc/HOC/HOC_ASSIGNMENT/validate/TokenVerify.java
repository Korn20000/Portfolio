
package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.validate;

import com.google.gson.annotations.Expose;

/**
 * Created by KoRn on 13-03-2018.
 *
 * getter and setter for verification of the token
 *
 */

public class TokenVerify
{

    @Expose
    private String stat;
    @Expose
    private int code;
    @Expose
    private String message;

    public String getStat()
    {
        return stat;
    }

    public void setStat(String stat)
    {
        this.stat = stat;
    }


    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
