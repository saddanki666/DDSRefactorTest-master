package com.dominion.mobile.ddsrefactortest.users.presenter;



import com.dominion.mobile.ddsrefactortest.users.view.UsersView;


/**
 * Created by saddanki on 13-04-2017.
 */

public class UsersPresenterImpl implements UsersPresenter {

    private UsersView userView;


    public UsersPresenterImpl(UsersView userView) {

        this.userView = userView;


    }

    @Override
    public void loadUsers() {

        userView.displayList();

    }


}
