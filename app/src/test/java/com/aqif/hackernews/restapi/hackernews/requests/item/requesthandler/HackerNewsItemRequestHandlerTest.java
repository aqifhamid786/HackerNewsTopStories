package com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler;

import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by aqifhamid.
 */
@RunWith(MockitoJUnitRunner.class)
public class HackerNewsItemRequestHandlerTest
{

    @Mock private IHackerNewsItemRetrofitService mService;
    @Mock private IHackerNewsItemCallbackHandler mCallbackHandler;
    private HackerNewsItemRequestHandler mRequestHandler;

    @Before
    public void beforeTest()
    {
        mRequestHandler = new HackerNewsItemRequestHandler(mService, mCallbackHandler);

    }

    @After
    public void afterTest()
    {
        mRequestHandler.clear();
        mRequestHandler = null;
    }

    @Test
    public void getHackerNewsItemCallbackObservable() throws Exception
    {
        assertNotNull(mCallbackHandler);
        assertEquals(mCallbackHandler, mRequestHandler.getHackerNewsItemCallbackObservable());
    }

    @Test
    public void clear() throws Exception
    {
        Object tag = new Object();
        mRequestHandler.setTag(tag);
        assertEquals(mRequestHandler.getTag(), tag);
        mRequestHandler.clear();
        assertNull(mRequestHandler.getTag());
    }

}