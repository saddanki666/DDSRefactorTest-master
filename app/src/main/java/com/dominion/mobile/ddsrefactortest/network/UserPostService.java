package com.dominion.mobile.ddsrefactortest.network;

import com.dominion.mobile.ddsrefactortest.api.UserPostsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by saddanki on 14-04-2017.
 */
public interface UserPostService {
    /**
     *  API call for getting users list
     * @return users array
     */
    @GET
    Call<UserPostsResponse> getUserPosts(@Url String url);
}
