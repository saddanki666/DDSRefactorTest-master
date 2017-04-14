package com.dominion.mobile.ddsrefactortest.users;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.dominion.mobile.ddsrefactortest.ApplicationModule;
import com.dominion.mobile.ddsrefactortest.R;
import com.dominion.mobile.ddsrefactortest.UserPostsActivity;
import com.dominion.mobile.ddsrefactortest.adapters.UsersAdapter;
import com.dominion.mobile.ddsrefactortest.api.UsersResponse;
import com.dominion.mobile.ddsrefactortest.api.entities.User;
import com.dominion.mobile.ddsrefactortest.network.UsersClient;
import com.dominion.mobile.ddsrefactortest.users.presenter.UsersPresenter;
import com.dominion.mobile.ddsrefactortest.users.presenter.UsersPresenterImpl;
import com.dominion.mobile.ddsrefactortest.users.view.UsersView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserActivity extends Activity implements UsersView {


    UsersAdapter adapter;
    private ProgressBar loadingIndicator;
    private UsersPresenter userPresenter;
    List<User> users = new ArrayList<>();
    ListView listView;

    @Inject
    UsersClient usersClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ((ApplicationModule) getApplication()).getComponent().inject(this);
        userPresenter = new UsersPresenterImpl(this);
        initializeUI();

    }

    /**
     * called to initializeUI
     */
    private void initializeUI() {

        listView = (ListView) findViewById(R.id.users);
        adapter = new UsersAdapter(this, users);
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(UserActivity.this, UserPostsActivity.class);
                intent.putExtra(UserPostsActivity.EXTRA_USER, users.get(position));
                startActivity(intent);
            }
        });
        userPresenter.loadUsers();

    }


    @Override
    protected void onResume() {
        super.onResume();
        showProgress();
        loadUsers();
    }



    @Override
    public void showProgress() {
        displayProgress(true);
    }

    @Override
    public void hideProgress() {
        displayProgress(false);
    }

    void displayProgress(boolean visibility) {
        if (visibility) {
            loadingIndicator.setVisibility(View.VISIBLE);
        } else {
            loadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void displayList() {

        loadUsers();

    }


    void updateadapter() {
        adapter.notifyDataSetChanged();
    }


    public void displayErrorMsg() {
        new AlertDialog.Builder(UserActivity.this).setTitle(R.string.error).setMessage(R.string.something_went_wrong).show();
    }

    public List<User> getUsers() {
        return users;
    }

    public void loadUsers() {
        Call<UsersResponse> getAllUsers = usersClient.getUsers();
        getAllUsers.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {

                // reset the users array, which was missing in the original code provided
                users.clear();
                users.addAll(response.body());
                updateadapter();
                hideProgress();
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                loadingIndicator.setVisibility(View.INVISIBLE);

                displayErrorMsg();
            }
        });
    }
}
