package com.dominion.mobile.ddsrefactortest.userposts.presenter;



import com.dominion.mobile.ddsrefactortest.userposts.view.UsersPostsView;


/**
 * Created by saddanki on 13-04-2017.
 */

public class UsersPostsPresenterImpl implements UsersPostsPresenter {

    private UsersPostsView userPostsView;




    public UsersPostsPresenterImpl(UsersPostsView userPostsView) {

        this.userPostsView = userPostsView;


    }

    @Override
    public void loadUsers() {

        userPostsView.displayList();

    }



}
