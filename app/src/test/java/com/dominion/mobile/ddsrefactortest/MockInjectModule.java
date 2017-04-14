package com.dominion.mobile.ddsrefactortest;

import android.app.Application;

import com.dominion.mobile.ddsrefactortest.api.UserPostsResponse;
import com.dominion.mobile.ddsrefactortest.api.UsersResponse;
import com.dominion.mobile.ddsrefactortest.inject.InjectModule;
import com.dominion.mobile.ddsrefactortest.network.UserPostClient;
import com.dominion.mobile.ddsrefactortest.network.UsersClient;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by saddanki on 14-04-2017.
 */
public class MockInjectModule extends InjectModule {

    private UsersResponse users;
    private UserPostsResponse userPosts;

    public MockInjectModule(Application app) {
        super(app);
    }

    @Override
    public UsersClient provideUsersClient() {
        UsersClient userClient = mock(UsersClient.class);


        final Call<UsersResponse> usersMockCall = mock(Call.class);
        doAnswer(new Answer<UsersResponse>() {
            @Override
            public UsersResponse answer(InvocationOnMock invocation) throws Throwable {
                Response<UsersResponse> res = Response.success(users);
                ((Callback) invocation.getArguments()[0]).onResponse(usersMockCall, res);
                return null;
            }
        }).when(usersMockCall).enqueue(any(Callback.class));


        final Call<UsersResponse> usersFailedMockCall = mock(Call.class);
        doAnswer(new Answer<UsersResponse>() {
            @Override
            public UsersResponse answer(InvocationOnMock invocation) throws Throwable {
                Response response = Response.error(406, ResponseBody.create(MediaType.parse("application/json"), "USERS_NOT_EXISTS".getBytes()));
                ((Callback) invocation.getArguments()[0]).onFailure(usersFailedMockCall, new HttpException(response));
                return null;
            }
        }).when(usersFailedMockCall).enqueue(any(Callback.class));


        when(userClient.getUsers()).thenReturn(usersMockCall, usersFailedMockCall);

        return userClient;
    }

    @Override
    public UserPostClient provideUserPostsClient() {
        UserPostClient postClient = mock(UserPostClient.class);


        final Call<UserPostsResponse> userPostsMockCall = mock(Call.class);
        doAnswer(new Answer<UserPostsResponse>() {
            @Override
            public UserPostsResponse answer(InvocationOnMock invocation) throws Throwable {
                Response<UserPostsResponse> res = Response.success(userPosts);
                ((Callback) invocation.getArguments()[0]).onResponse(userPostsMockCall, res);
                return null;
            }
        }).when(userPostsMockCall).enqueue(any(Callback.class));


        final Call<UserPostsResponse> userPostsFailedMockCall = mock(Call.class);
        doAnswer(new Answer<UserPostsResponse>() {
            @Override
            public UserPostsResponse answer(InvocationOnMock invocation) throws Throwable {
                Response response = Response.error(406, ResponseBody.create(MediaType.parse("application/json"), "USER_POSTS_NOT_EXISTS".getBytes()));
                ((Callback) invocation.getArguments()[0]).onFailure(userPostsFailedMockCall, new HttpException(response));
                return null;
            }
        }).when(userPostsFailedMockCall).enqueue(any(Callback.class));


        when(postClient.getUserPosts(any(String.class))).thenReturn(userPostsMockCall, userPostsFailedMockCall);
        return postClient;
    }

    /**
     * sets user results
     * @param usersResults expected
     */
    public void setUsersResult(UsersResponse usersResults) {
        this.users = usersResults;
    }

    /**
     * sets user post result
     * @param userPostsResults
     */
    public void setUserPostsResult(UserPostsResponse userPostsResults) {
        this.userPosts = userPostsResults;
    }
}
