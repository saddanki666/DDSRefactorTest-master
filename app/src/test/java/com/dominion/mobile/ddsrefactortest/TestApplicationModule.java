package com.dominion.mobile.ddsrefactortest;

import com.dominion.mobile.ddsrefactortest.inject.InjectModule;

/**
 * Created by saddanki on 14-04-2017.
 */
public class TestApplicationModule extends ApplicationModule {
    private InjectModule mApplicationModule;

    @Override
    InjectModule getApplicationModule() {
        if (mApplicationModule == null) {
            return super.getApplicationModule();
        }
        return mApplicationModule;
    }

    public void setApplicationModule(InjectModule mApplicationModule) {
        this.mApplicationModule = mApplicationModule;
        initComponent();
    }
}
