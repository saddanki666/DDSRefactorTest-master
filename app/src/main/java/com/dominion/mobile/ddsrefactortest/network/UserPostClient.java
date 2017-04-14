package com.dominion.mobile.ddsrefactortest.network;

import android.content.Context;

import com.dominion.mobile.ddsrefactortest.api.UserPostsResponse;

import retrofit2.Call;

/**
 * Created by saddanki on 14-04-2017.
 */

public class UserPostClient {
    private UserPostService mClient;


    /**
     * rest adapter instance through retrofit

     */
    public UserPostClient() {

        this.mClient = RetrofitInstance.getRestAdapter().create(UserPostService.class);
    }

    /**
     * list of users through retrofit
     * @return UsersResponse response
     */
    public Call<UserPostsResponse> getUserPosts(String url) {
        return mClient.getUserPosts(url);
    }
}