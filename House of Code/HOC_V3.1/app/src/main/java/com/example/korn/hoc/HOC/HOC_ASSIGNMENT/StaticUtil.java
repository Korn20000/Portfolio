package com.example.korn.hoc.HOC.HOC_ASSIGNMENT;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class StaticUtil
{

    public static Dialog showOkAlert(Context context, SpannableString message)
    {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
        alt_bld.setCancelable(false);

        final SpannableString linkMessage = new SpannableString(message);
        Linkify.addLinks(linkMessage, Linkify.ALL);

        alt_bld.setMessage(linkMessage);

        alt_bld.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // Action for 'OK' Button
                dialog.dismiss();
            }
        });

        AlertDialog alert = alt_bld.create();
        alert.show();

        // Enable clickable links
        TextView text = alert.findViewById(android.R.id.message);
        if(null != text)
        {
            text.setMovementMethod(LinkMovementMethod.getInstance());
            text.setLinksClickable(true);
            text.setAutoLinkMask(Linkify.ALL);
        }

        return alert;
    }

    /*
     * Generate the MD5 hash from an input string
     */
    public static String getMD5Hash(String encTarget)
    {
        MessageDigest mdEnc = null;
        try
        {
            mdEnc = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        }
        // Encryption algorithm
        mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);

        while ( md5.length() < 32 )
        {
            md5 = "0"+md5;
        }

        return md5;
    }



    public static void addPairToSpannable(final SpannableStringBuilder builder, final String label, final String value)
    {
        int start = builder.length();

        builder.append('\n');
        builder.append(label);
        builder.append('\n');

        int end = builder.length();
        builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append('\t');
        builder.append(value);
        builder.append('\n');
    }
}
