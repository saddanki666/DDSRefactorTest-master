package com.dominion.mobile.ddsrefactortest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dominion.mobile.ddsrefactortest.api.UserPostsResponse;
import com.dominion.mobile.ddsrefactortest.api.entities.User;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by saddanki on 14-04-2017.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApplicationModule.class)

public class UserPostsActivityTest {

    private UserPostsActivity myUserPostsActivity;
    private UserPostsResponse userPostsMocked;


    private ActivityController<UserPostsActivity> controller;

    @Before
    public void setUp() throws Exception {
        TestApplicationModule app = (TestApplicationModule) RuntimeEnvironment.application;

        MockInjectModule module = new MockInjectModule(app);
        userPostsMocked = createMockedUserPostsData();
        module.setUserPostsResult(userPostsMocked);
        app.setApplicationModule(module);

        controller = Robolectric.buildActivity(UserPostsActivity.class);
        createIntent(getMockedUserData());
    }

    /**
     * Activity creation that allows intent extras to be passed in
     *
     * @param user object for intent extras
     */
    private void createIntent(User user) {
        Intent intent = new Intent();
        intent.putExtra("extra_user", user);
        myUserPostsActivity = controller.withIntent(intent)
                .create()
                .resume()
                .get();
    }

    @Test
    public void test_activityNotNull() throws Exception {
        // check for activity created
        assertNotNull(myUserPostsActivity);
    }

    @Test
    public void test_userPostsAdapter() throws Exception {
        // mocking 5 items
        ListView listView = (ListView) myUserPostsActivity.findViewById(R.id.posts);
        assertEquals(listView.getAdapter().getCount(), 5);

        // testing get view method and verifying first element is matching with mocked object
        View view = listView.getAdapter().getView(0, null, listView);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView body = (TextView) view.findViewById(R.id.body);
        Assert.assertEquals(title.getText().toString(), userPostsMocked.get(0).getTitle());
        Assert.assertEquals(body.getText().toString(), userPostsMocked.get(0).getBody());
    }

    @Test
    public void test_PostNetworkCallFail() throws Exception {
        // mocking  2nd time

        myUserPostsActivity.getUserPostsFromServer();
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

        Dialog alertDialog = ShadowAlertDialog.getLatestDialog();
        assertNotNull(alertDialog);
        assertTrue(alertDialog.isShowing());
    }

    @After
    public void tearDown() throws Exception {

        controller.pause()
                .stop()
                .destroy();
    }

    /**
     * mocked user post object for testing
     *
     * @return UserPostsResponse object
     */
    private UserPostsResponse createMockedUserPostsData() {
        String mockResponse = "[{\"userId\":1,\"id\":1,\"title\":\"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\":\"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"},{\"userId\":1,\"id\":2,\"title\":\"qui est esse\",\"body\":\"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"},{\"userId\":1,\"id\":3,\"title\":\"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\"body\":\"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"},{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"},{\"userId\":1,\"id\":5,\"title\":\"nesciunt quas odio\",\"body\":\"repudiandae veniam quaerat sunt sed\\nalias aut fugiat sit autem sed est\\nvoluptatem omnis possimus esse voluptatibus quis\\nest aut tenetur dolor neque\"}]";
        return new Gson().fromJson(mockResponse, UserPostsResponse.class);

    }

    /**
     * mocked user object for testing
     *
     * @return User object
     */
    private User getMockedUserData() {
        String mockUser = "{\"id\":1,\"name\":\"LeanneGraham\",\"username\":\"Bret\",\"email\":\"Sincere@april.biz\",\"address\":{\"street\":\"KulasLight\",\"suite\":\"Apt.556\",\"city\":\"Gwenborough\",\"zipcode\":\"92998-3874\",\"geo\":{\"lat\":\"-37.3159\",\"lng\":\"81.1496\"}}}";
        return new Gson().fromJson(mockUser, User.class);

    }

}
