package com.vpmsbcm.service;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

//Singleton
enum Connection
{
    INSTANCE;

   private final GigaSpace space;

    Connection()
    {
        System.out.println("Connecting to data grid");
        UrlSpaceConfigurer configurer = new UrlSpaceConfigurer("jini://*/*/myGrid");
        configurer.lookupGroups("gigaspaces-10.0.1-XAPPremium-ga");
        space = new GigaSpaceConfigurer(configurer).create();
    }

    public static Connection instance()
    {
        return INSTANCE;
    }

    public GigaSpace getSpace()
    {
        return space;
    }
}