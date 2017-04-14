package com.dominion.mobile.ddsrefactortest.users;


import android.app.Dialog;
import android.content.Intent;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dominion.mobile.ddsrefactortest.BuildConfig;
import com.dominion.mobile.ddsrefactortest.MockInjectModule;
import com.dominion.mobile.ddsrefactortest.R;
import com.dominion.mobile.ddsrefactortest.TestApplicationModule;
import com.dominion.mobile.ddsrefactortest.UserPostsActivity;
import com.dominion.mobile.ddsrefactortest.api.UsersResponse;
import com.google.gson.Gson;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;

import org.robolectric.shadows.ShadowLooper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by saddanki on 14-04-2017.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApplicationModule.class, sdk = 21)
public class UserActivityTest {

    private UserActivity userActivity;
    private ListView listView;
    private UsersResponse usersMocked;

    @Before
    public void setUp() throws Exception {
        TestApplicationModule app = (TestApplicationModule) RuntimeEnvironment.application;

        MockInjectModule module = new MockInjectModule(app);
        usersMocked = createMockedUsersList();
        module.setUsersResult(usersMocked);
        app.setApplicationModule(module);

        userActivity = Robolectric.setupActivity(UserActivity.class);
        listView = (ListView) userActivity.findViewById(R.id.users);
    }

    @Test
    public void activityNotNull() throws Exception {

        assertNotNull(userActivity);
    }

    /**
     * Creating a mocked list of users for testing
     *
     * @return array of users created
     */
    private UsersResponse createMockedUsersList() {
        String mockResponse = "[{\"id\":1,\"name\":\"Leanne Graham\",\"username\":\"Bret\",\"email\":\"Sincere@april.biz\",\"address\":{\"street\":\"Kulas Light\",\"suite\":\"Apt. 556\",\"city\":\"Gwenborough\",\"zipcode\":\"92998-3874\",\"geo\":{\"lat\":\"-37.3159\",\"lng\":\"81.1496\"}},\"phone\":\"1-770-736-8031 x56442\",\"website\":\"hildegard.org\",\"company\":{\"name\":\"Romaguera-Crona\",\"catchPhrase\":\"Multi-layered client-server neural-net\",\"bs\":\"harness real-time e-markets\"}},{\"id\":2,\"name\":\"Ervin Howell\",\"username\":\"Antonette\",\"email\":\"Shanna@melissa.tv\",\"address\":{\"street\":\"Victor Plains\",\"suite\":\"Suite 879\",\"city\":\"Wisokyburgh\",\"zipcode\":\"90566-7771\",\"geo\":{\"lat\":\"-43.9509\",\"lng\":\"-34.4618\"}},\"phone\":\"010-692-6593 x09125\",\"website\":\"anastasia.net\",\"company\":{\"name\":\"Deckow-Crist\",\"catchPhrase\":\"Proactive didactic contingency\",\"bs\":\"synergize scalable supply-chains\"}}]";
        return new Gson().fromJson(mockResponse, UsersResponse.class);

    }

    @Test
    public void test_failedResponseFromServer() throws Exception {

        userActivity.loadUsers();

        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();


        Dialog alertDialog = ShadowAlertDialog.getLatestDialog();
        assertNotNull(alertDialog);
        assertTrue(alertDialog.isShowing());
    }


    @Test
    public void test_usersListItemClick() throws Exception {


        boolean clicked = listView.performItemClick(listView, 0, listView.getItemIdAtPosition(0));
        assertTrue(clicked);

        Intent expectedIntent = new Intent(userActivity, UserPostsActivity.class);
        ShadowActivity shadowActivity = shadowOf(userActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        junit.framework.Assert.assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void test_usersListAdapter() throws Exception {

        assertEquals(listView.getAdapter().getCount(), 2);
        View view = listView.getAdapter().getView(0, null, listView);
        TextView title = (TextView) view.findViewById(android.R.id.text1);
        Assert.assertEquals(title.getText().toString(), usersMocked.get(0).getName());
    }


}