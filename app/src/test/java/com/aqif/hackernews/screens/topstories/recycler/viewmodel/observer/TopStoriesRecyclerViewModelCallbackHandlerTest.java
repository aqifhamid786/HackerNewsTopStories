package com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer;

import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;

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
public class TopStoriesRecyclerViewModelCallbackHandlerTest
{

    @Mock private ITopStoriesRecyclerViewModelCallbackObserver mObserverA;
    @Mock private ITopStoriesRecyclerViewModelCallbackObserver mObserverB;
    private TopStoriesRecyclerViewModelCallbackHandler mCallbackHandler;
    private ArrayList<ITopStoriesRecyclerViewModelCallbackObserver> mObservers;

    @Before
    public void beforeTest()
    {
        mObservers = new ArrayList<>();
        mCallbackHandler = new TopStoriesRecyclerViewModelCallbackHandler(mObservers);

    }

    @After
    public void afterTest()
    {
        mObservers.clear();
    }

    @Test
    public void registerTopStoriesRecyclerViewModelCallbackObserver() throws Exception
    {
        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(null);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),2);
    }

    @Test
    public void unregisterTopStoriesRecyclerViewModelCallbackObserver() throws Exception
    {
        mCallbackHandler.unregisterTopStoriesRecyclerViewModelCallbackObserver(null);
        assertEquals(mObservers.size(),0);

        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(mObserverA);
        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(mObserverB);

        mCallbackHandler.unregisterTopStoriesRecyclerViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterTopStoriesRecyclerViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterTopStoriesRecyclerViewModelCallbackObserver(null);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterTopStoriesRecyclerViewModelCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),0);
    }

    @Test
    public void notifyLoadMoreTopStories() throws Exception
    {
        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(mObserverA);
        mCallbackHandler.notifyLoadMoreTopStories();
        Mockito.verify(mObserverA).loadMoreTopStories();
    }

    @Test
    public void notifyTopStoryClicked() throws Exception
    {
        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(mObserverA);
        TopStoryBinderModel topStoryBinderModelMock = Mockito.mock(TopStoryBinderModel.class);

        mCallbackHandler.notifyTopStoryClicked(topStoryBinderModelMock);
        Mockito.verify(mObserverA).topStoryClicked(topStoryBinderModelMock);
    }

    @Test
    public void clear() throws Exception
    {
        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(mObserverA);
        mCallbackHandler.registerTopStoriesRecyclerViewModelCallbackObserver(mObserverB);
        assertEquals(mObservers.size(), 2);
        mCallbackHandler.clear();
        assertEquals(mObservers.size(), 0);
    }


}