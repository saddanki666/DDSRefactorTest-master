package com.dominion.mobile.ddsrefactortest.inject;

import android.app.Application;

import com.dominion.mobile.ddsrefactortest.network.UserPostClient;
import com.dominion.mobile.ddsrefactortest.network.UsersClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saddanki on 14-04-2017.
 */
@Module
public class InjectModule {
    private Application mApp;

    /**
     * Application instance
     *
     * @param app {@link Application}
     */
    public InjectModule(Application app) {
        mApp = app;
    }

    /**
     * UsersClient instance
     *
     * @return UsersClient
     */
    @Provides
    @Singleton
    public UsersClient provideUsersClient() {
        return new UsersClient();
    }

    /**
     * UserPostClient instance
     *
     * @return UserPostClient {@link UserPostClient}
     */
    @Provides
    @Singleton
    public UserPostClient provideUserPostsClient() {
        return new UserPostClient();
    }

    /**
     *  application instance
     *
     * @return Application instance {@link Application}
     */
    @Provides
    @Singleton
    Application provideApplication() {
        return mApp;
    }
}
