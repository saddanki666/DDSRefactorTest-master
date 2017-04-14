/*
 * Copyright 2016 Dominion Enterprises. All Rights Reserved.
 */

package com.dominion.mobile.ddsrefactortest.api.entities;

/**
 * Represents a user's post
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class Post {
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public String getBody() {
        return body;
    }


    private int id;

    private String title;
    private String body;
}