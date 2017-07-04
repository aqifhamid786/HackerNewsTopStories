package com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler;

import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.IHackerNewsTopStoriesCallbackHandler;

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
public class HackerNewsTopStoriesRequestHandlerTest
{

    @Mock private IHackerNewsTopStoriesRetrofitService mService;
    @Mock private IHackerNewsTopStoriesCallbackHandler mCallbackHandler;
    private HackerNewsTopStoriesRequestHandler mRequestHandler;

    @Before
    public void beforeTest()
    {
        mRequestHandler = new HackerNewsTopStoriesRequestHandler(mService, mCallbackHandler);
    }

    @After
    public void afterTest()
    {
        mRequestHandler.clear();
        mRequestHandler = null;
    }

    @Test
    public void getHackerNewsTopStoriesCallbackObservable() throws Exception
    {
        assertNotNull(mRequestHandler);
        assertEquals(mCallbackHandler, mRequestHandler.getHackerNewsTopStoriesCallbackObservable());
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