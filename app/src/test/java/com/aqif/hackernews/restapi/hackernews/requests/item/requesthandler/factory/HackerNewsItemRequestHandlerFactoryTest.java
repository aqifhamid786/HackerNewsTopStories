package com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.factory;

import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.factory.HackerNewsItemCallbackHandlerFactory;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRetrofitService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class HackerNewsItemRequestHandlerFactoryTest
{
    private HackerNewsItemRequestHandlerFactory mHackerNewsItemRequestHandlerFactory;
    @Mock private IHackerNewsItemRetrofitService mService;

    @Before
    public void beforeTest()
    {
        mHackerNewsItemRequestHandlerFactory = new HackerNewsItemRequestHandlerFactory(mService, new HackerNewsItemCallbackHandlerFactory());
    }

    @After
    public void afterTest()
    {
        mHackerNewsItemRequestHandlerFactory = null;
    }

    @Test
    public void getHackerNewsItemRequestHandlerInstance() throws Exception
    {
        IHackerNewsItemRequestHandler handlerA = mHackerNewsItemRequestHandlerFactory.getHackerNewsItemRequestHandlerInstance();
        IHackerNewsItemRequestHandler handlerB = mHackerNewsItemRequestHandlerFactory.getHackerNewsItemRequestHandlerInstance();

        assertNotNull(handlerA);
        assertNotNull(handlerA.getHackerNewsItemCallbackObservable());
        assertNotEquals(handlerA, handlerB);

    }

}