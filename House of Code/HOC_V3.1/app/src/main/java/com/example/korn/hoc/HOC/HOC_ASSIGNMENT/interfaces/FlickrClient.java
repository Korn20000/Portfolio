package com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interfaces;

import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.validate.StatusResponse;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.validate.TokenVerify;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.interstingphotos.PublicInterestingPics;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.privatephotoset.PrivatePhotoSet;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.publicphotosets.Public_Photosets;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.userinfo.UserData;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.login.UsernameResponse;
import com.example.korn.hoc.HOC.HOC_ASSIGNMENT.validate.TokenValidation;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by KoRn on 14-03-2018.
 *
 *
 */
public interface FlickrClient
{

    String baseURL = "https://api.flickr.com";


    @GET("/services/rest/?method=flickr.auth.oauth.checkToken")
    TokenVerify validateToken(@Query("oauth_token") String oauth_token);

    @GET("/services/rest/?method=flickr.auth.oauth.checkToken")
    void validateToken(@Query("oauth_token") String oauth_token, Callback<TokenVerify> cb);


    /*
     * Retrieve userId of logged in user
     */
    @GET("/services/rest/?method=flickr.test.login")
    void testToken(Callback<TokenValidation> cb);

    @GET("/services/rest/?method=flickr.people.getInfo")
    UserData getUserData(@Query("user_id") String userID);

    @GET("/services/rest/?method=flickr.people.getInfo")
    void getUserData(@Query("user_id") String userID, Callback<UserData> cb);

    @GET("/services/rest/?method=flickr.people.findByUsername")
    void getUserIdFromName(@Query("username") String username, Callback<UsernameResponse> cb);



    /*
     * Get photo list from a named set and user.  No signature required.
     * TODO handle extras such as extras, per_page, page, privacy_filter, and media
     */
    @GET("/services/rest/?method=flickr.photosets.getPhotos")
    PrivatePhotoSet getPhotoset(@Query("photoset_id") String photosetId, @Query("user_id") String userId);

    @GET("/services/rest/?method=flickr.photosets.getPhotos")
    void getPhotoset(@Query("photoset_id") String photosetId, @Query("user_id") String userId, Callback<PrivatePhotoSet> cb);


    /*
     * Get list of photosets for a particular user; current user if no ID is specified
     * Optional params not yet implemented: user_id, page, per_page, primary_photo_extras
     */
    @GET("/services/rest/?method=flickr.photosets.getList")
    void getPhotosetList(@Query("user_id") String userId, Callback<Public_Photosets> cb);

    @GET("/services/rest/?method=flickr.interestingness.getList")
    void getInterestingness(@Query("per_page") String perPage, @Query("page") String page, Callback<PublicInterestingPics> cb);

    @GET("/services/rest/?method=flickr.favorites.add")
    void addFavorite(@Query("photo_id") String photoId, Callback<StatusResponse> cb);

    @GET("/services/rest/?method=flickr.favorites.remove")
    void removeFavorite(@Query("photo_id") String photoId, Callback<StatusResponse> cb);
}

