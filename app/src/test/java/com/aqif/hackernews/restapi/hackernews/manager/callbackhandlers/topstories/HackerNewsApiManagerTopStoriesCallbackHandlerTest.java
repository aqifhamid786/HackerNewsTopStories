package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by aqifhamid.
 */
@RunWith(MockitoJUnitRunner.class)
public class HackerNewsApiManagerTopStoriesCallbackHandlerTest
{

    @Mock private IHackerNewsApiManagerTopStoriesCallbackObserver mObserverA;
    @Mock private IHackerNewsApiManagerTopStoriesCallbackObserver mObserverB;
    private ArrayList<IHackerNewsApiManagerTopStoriesCallbackObserver> mObservers;
    private HackerNewsApiManagerTopStoriesCallbackHandler mCallbackHandler;

    @Before
    public void beforeTest()
    {
        mObservers = new ArrayList<>();
        mCallbackHandler = new HackerNewsApiManagerTopStoriesCallbackHandler(mObservers);
    }

    @After
    public void afterTest()
    {
        mObservers.clear();
        mCallbackHandler = null;
    }

    @Test
    public void registerHackerNewsApiManagerTopStoriesCallbackObserver() throws Exception
    {

        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(null);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),2);

    }

    @Test
    public void unregisterHackerNewsApiManagerTopStoriesCallbackObserver() throws Exception
    {

        mCallbackHandler.unregisterHackerNewsApiManagerTopStoriesCallbackObserver(null);
        assertEquals(mObservers.size(),0);

        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(mObserverA);
        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(mObserverB);

        mCallbackHandler.unregisterHackerNewsApiManagerTopStoriesCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsApiManagerTopStoriesCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsApiManagerTopStoriesCallbackObserver(null);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsApiManagerTopStoriesCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),0);
    }

    @Test
    public void notifyTopStoriesSuccess() throws Exception
    {

        IHackerNewsApiManagerTopStoriesCallbackObserver observer = Mockito.spy(mObserverA);
        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(observer);

        int[] storiesIds = new int[0];

        Mockito.verify(observer, Mockito.times(0)).onTopStoriesSuccess(Mockito.eq(storiesIds));
        mCallbackHandler.notifyTopStoriesSuccess(storiesIds);
        Mockito.verify(observer).onTopStoriesSuccess(Mockito.eq(storiesIds));

    }

    @Test
    public void notifyTopStoriesFailure() throws Exception
    {

        IHackerNewsApiManagerTopStoriesCallbackObserver observer = Mockito.spy(mObserverA);
        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(observer);

        mCallbackHandler.notifyTopStoriesFailure("");
        Mockito.verify(observer).onTopStoriesFailure(Mockito.anyString());
        mCallbackHandler.notifyTopStoriesFailure("");
        Mockito.verify(observer, Mockito.times(2)).onTopStoriesFailure(Mockito.anyString());

    }

    @Test
    public void clear()
    {
        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(mObserverA);
        mCallbackHandler.registerHackerNewsApiManagerTopStoriesCallbackObserver(mObserverB);
        assertEquals(mObservers.size(), 2);
        mCallbackHandler.clear();
        assertEquals(mObservers.size(), 0);
    }

}