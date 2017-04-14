/*
 * Copyright 2016 Dominion Enterprises. All Rights Reserved.
 */

package com.dominion.mobile.ddsrefactortest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.dominion.mobile.ddsrefactortest.adapters.UserPostsAdapter;
import com.dominion.mobile.ddsrefactortest.api.UserPostsResponse;
import com.dominion.mobile.ddsrefactortest.api.entities.Post;
import com.dominion.mobile.ddsrefactortest.api.entities.User;
import com.dominion.mobile.ddsrefactortest.network.UserPostClient;
import com.dominion.mobile.ddsrefactortest.userposts.presenter.UsersPostsPresenter;
import com.dominion.mobile.ddsrefactortest.userposts.presenter.UsersPostsPresenterImpl;
import com.dominion.mobile.ddsrefactortest.userposts.view.UsersPostsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity that lists
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class UserPostsActivity extends Activity implements UsersPostsView {

    public static final String EXTRA_USER = "extra_user";


    private UserPostsAdapter adapter;
    private User user;
    private ProgressBar loadingIndicator;

    private final List<Post> posts = new ArrayList<>();
    private UsersPostsPresenter userPostsPresenter;

    @Inject
    UserPostClient userPostsClient;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_posts);

        ((ApplicationModule) getApplication()).getComponent().inject(this);

        userPostsPresenter = new UsersPostsPresenterImpl(this);
        adapter = new UserPostsAdapter(this, posts);

        ListView listView = (ListView) findViewById(R.id.posts);
        listView.setAdapter(adapter);

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        user = (User) getIntent().getExtras().get(EXTRA_USER);
    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

        displayProgress(true);
        userPostsPresenter.loadUsers();

    }




    @Override
    public void displayList() {
        getUserPostsFromServer();
    }

    void displayProgress(boolean visibility) {
        if (visibility) {
            loadingIndicator.setVisibility(View.VISIBLE);
        } else {
            loadingIndicator.setVisibility(View.INVISIBLE);
        }
    }


    public void getUserPostsFromServer() {
        Call<UserPostsResponse> getAllUserPosts = userPostsClient.getUserPosts("https://jsonplaceholder.typicode.com/posts?userId=" + user.getId());
        getAllUserPosts.enqueue(new Callback<UserPostsResponse>() {
            @Override
            public void onResponse(Call<UserPostsResponse> call, Response<UserPostsResponse> response) {

                // reset the users array, which was missing in the original code provided
                posts.clear();
                posts.addAll(response.body());
                adapter.notifyDataSetChanged();
                displayProgress(false);
            }

            @Override
            public void onFailure(Call<UserPostsResponse> call, Throwable t) {
                displayProgress(false);

                displayErrorMsg();
            }
        });
    }

    public void displayErrorMsg() {
        new AlertDialog.Builder(UserPostsActivity.this).setTitle(R.string.error).setMessage(R.string.something_went_wrong).show();
    }


}