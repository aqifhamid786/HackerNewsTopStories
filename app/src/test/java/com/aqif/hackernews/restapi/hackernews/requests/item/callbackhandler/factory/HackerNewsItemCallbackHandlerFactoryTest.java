package com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.factory;

import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by aqifhamid.
 */

public class HackerNewsItemCallbackHandlerFactoryTest
{

    private HackerNewsItemCallbackHandlerFactory mHackerNewsItemCallbackHandlerFactory;

    @Before
    public void beforeTest()
    {
        mHackerNewsItemCallbackHandlerFactory = new HackerNewsItemCallbackHandlerFactory();

    }

    @After
    public void afterTest()
    {
        mHackerNewsItemCallbackHandlerFactory = null;
    }

    @Test
    public void getHackerNewsItemCallbackHandlerInstance() throws Exception
    {
        IHackerNewsItemCallbackHandler handlerA = mHackerNewsItemCallbackHandlerFactory.getHackerNewsItemCallbackHandlerInstance();
        IHackerNewsItemCallbackHandler handlerB = mHackerNewsItemCallbackHandlerFactory.getHackerNewsItemCallbackHandlerInstance();

        assertNotNull(handlerA);
        assertNotEquals(handlerA, handlerB);
    }

}