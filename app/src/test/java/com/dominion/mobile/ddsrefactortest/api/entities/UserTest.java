package com.dominion.mobile.ddsrefactortest.api.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by saddanki on 14-04-2017.
 */
public class UserTest {

    private User user;
    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void notNull() {
        assertNotNull(user);
    }

    @Test
    public void test_Update() throws Exception {
        user.setName("Sravanth");
        assertEquals(user.getName(), "Sravanth");
    }



}