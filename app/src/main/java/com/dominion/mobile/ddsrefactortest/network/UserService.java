package com.dominion.mobile.ddsrefactortest.network;

import com.dominion.mobile.ddsrefactortest.api.UsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by saddanki on 14-04-2017.
 */
public interface UserService {

    /**
     * API call for getting list of users
     *
     * @return users array
     */
    @GET("users")
    Call<UsersResponse> getUsers();
}
