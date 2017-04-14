/*
 * Copyright 2016 Dominion Enterprises. All Rights Reserved.
 */

package com.dominion.mobile.ddsrefactortest.api.entities;

import java.io.Serializable;

/**
 * Represents a user
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class User implements Serializable
{
    public int getId()
    {
        return id;
    }
    
    public void setId( final int id )
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName( final String name )
    {
        this.name = name;
    }

    
    private int id;
    private String name;

}