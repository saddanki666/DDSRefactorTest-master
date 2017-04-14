package com.dominion.mobile.ddsrefactortest.network;

import android.content.Context;

import com.dominion.mobile.ddsrefactortest.api.UsersResponse;

import retrofit2.Call;

/**
 * Created by saddanki on 14-04-2017.
 */
public class UsersClient {

    private UserService mClient;


    /**
     *  rest adapter instance through retrofit
     *
     */
    public UsersClient() {

        this.mClient = RetrofitInstance.getRestAdapter().create(UserService.class);
    }

    /**
     * users list through retrofit
     * @return UsersResponse response
     */
    public Call<UsersResponse> getUsers() {
        return mClient.getUsers();
    }
}
