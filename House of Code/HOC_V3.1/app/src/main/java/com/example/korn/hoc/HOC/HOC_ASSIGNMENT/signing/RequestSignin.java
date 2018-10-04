package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.signing;

import android.util.Log;
import android.util.Pair;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.Constants;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.StaticUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * Created by KoRn on 14-03-2018.
 *
 * Request sign-in separator from OAuth
 */
public class RequestSignin
{

    protected static final String signaturArg = "&api_sig=";
    //Stat an other argument pair
    protected static final String argSeparator = "&";
    //Argument value
    protected static final String equArg = "=";
    //start of a method pair
    protected static final String mehtodArg = "\\?";
    //json format that combines the arguments
    protected static final String format = "&format=json&nojsoncallback=1";
    protected static final String apiArg = "&api_key=";


    public static String signRequest(final String unsorted, final String tokenSecret)
    {
        ArrayList<Pair<String,String>> argList = new ArrayList<>();
        StringBuilder signatureStringBuilder = new StringBuilder();

        //0 is set as default and so is the method, and all key values are pairs
        String[] kvPairs = unsorted.split(argSeparator);

        // Separating the key values
        for(int i = 1; i < kvPairs.length; i++) {
            String[] pair = kvPairs[i].split(equArg);
            argList.add(new Pair<>(pair[0], pair[1]));

            if(Constants.debugTag) {
                Log.i("signRequest", "Adding " + pair[0] + ", " + pair[1]);
            }
        }

        // Alphabetically sorting the arguments
        Collections.sort(argList, new PairComparator());

        String[] methodPair = kvPairs[0].split(mehtodArg)[1].split(equArg);
        argList.add(new Pair<>(methodPair[0], methodPair[1]));

        /*
         *
         * Combine the single strings' arguments, where names and values are paired but does not separated whit
         * separators or whitespaces
         */
        for(int i = 0; i < argList.size(); i++)
        {
            signatureStringBuilder.append(argList.get(i).first);
            signatureStringBuilder.append(argList.get(i).second);
        }

        final String signature = generateSignature(signatureStringBuilder.toString(), tokenSecret);

        if(Constants.debugTag)
        {
            Log.i("signRequest", "signature: " + signature);
        }

        final String signed = unsorted + signaturArg + signature;

        if(Constants.debugTag)
        {
            Log.i("signRequest", "final url: " + signed);
        }

        return signed;
    }

    /*
     * The URL for Flickr is generated from all the arguments, sorted and concatenated.
     */
    protected static String generateSignature(final String argList, final String tokenSecret) {
        final String signingString =  tokenSecret + argList;

        final String signature = StaticUtil.getMD5Hash(signingString);

        if(Constants.debugTag) {
            Log.i("generateSignature", "signing string: " + signingString);
            Log.i("generateSignature", "signature: " + signature);
        }

        return signature;
    }

    /*
     * Alphabetically sort by the first string in the pair
     */
    protected static class PairComparator implements Comparator<Pair<String,String>> {
        public int compare(Pair<String,String> pair1, Pair<String,String> pair2){
            return pair1.first.compareTo(pair2.first) ;
        }
    }


    /*
     * All calls to Flickr will appended before the signature is calculated
     */
    public static String addStaticParams(final String url) {
        return url + format + apiArg + Constants.apiKey;
    }
}
