package com.dominion.mobile.ddsrefactortest.inject;


import com.dominion.mobile.ddsrefactortest.UserPostsActivity;
import com.dominion.mobile.ddsrefactortest.users.UserActivity;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by saddanki on 14-04-2017.
 */
@Singleton
@Component(modules = InjectModule.class)
public interface InjectComponent {
    /**
     * UserActivity dependency
     *
     * @param activity of {@link UserActivity}
     */
    void inject(UserActivity activity);

    /**
     * UserPostsActivity dependency
     *
     * @param activity of {@link UserPostsActivity}
     */
    void inject(UserPostsActivity activity);


}
