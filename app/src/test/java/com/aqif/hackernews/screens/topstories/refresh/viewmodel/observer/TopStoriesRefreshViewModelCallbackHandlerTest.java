package com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer;

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
public class TopStoriesRefreshViewModelCallbackHandlerTest
{

    @Mock private ITopStoriesRefreshViewModelCallbackObserver mObserverA;
    @Mock private ITopStoriesRefreshViewModelCallbackObserver mObserverB;
    private ArrayList<ITopStoriesRefreshViewModelCallbackObserver> mObservers;
    private TopStoriesRefreshViewModelCallbackHandler mCallbackHandler;

    @Before
    public void beforeTest()
    {
        mObservers = new ArrayList<>();
        mCallbackHandler = new TopStoriesRefreshViewModelCallbackHandler(mObservers);
    }

    @After
    public void afterTest()
    {
        mObservers.clear();
    }

    @Test
    public void registerTopStoriesRefreshViewModelCallbackObserver() throws Exception
    {
        mCallbackHandler.registerTopStoriesRefreshViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.registerTopStoriesRefreshViewModelCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerTopStoriesRefreshViewModelCallbackObserver(null);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerTopStoriesRefreshViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),2);
    }

    @Test
    public void unregisterTopStoriesRefreshViewModelCallbackObserver() throws Exception
    {
        mCallbackHandler.unregisterTopStoriesRefreshViewModelCallbackObserver(null);
        assertEquals(mObservers.size(),0);

        mCallbackHandler.registerTopStoriesRefreshViewModelCallbackObserver(mObserverA);
        mCallbackHandler.registerTopStoriesRefreshViewModelCallbackObserver(mObserverB);

        mCallbackHandler.unregisterTopStoriesRefreshViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterTopStoriesRefreshViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterTopStoriesRefreshViewModelCallbackObserver(null);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterTopStoriesRefreshViewModelCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),0);
    }

    @Test
    public void notifyRefresh() throws Exception
    {
        mCallbackHandler.registerTopStoriesRefreshViewModelCallbackObserver(mObserverA);
        mCallbackHandler.notifyRefresh();
        Mockito.verify(mObserverA).onRefresh();
    }

    @Test
    public void clear() throws Exception
    {
        mCallbackHandler.registerTopStoriesRefreshViewModelCallbackObserver(mObserverA);
        mCallbackHandler.registerTopStoriesRefreshViewModelCallbackObserver(mObserverB);
        assertEquals(mObservers.size(), 2);
        mCallbackHandler.clear();
        assertEquals(mObservers.size(), 0);
    }


}