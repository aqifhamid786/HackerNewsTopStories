package com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer;

import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;

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
public class StoryCommentsRecyclerViewModelCallbackHandlerTest
{
    @Mock private IStoryCommentsRecyclerViewModelCallbackObserver mObserverA;
    @Mock private IStoryCommentsRecyclerViewModelCallbackObserver mObserverB;
    private ArrayList<IStoryCommentsRecyclerViewModelCallbackObserver> mObservers;
    private StoryCommentsRecyclerViewModelCallbackHandler mCallbackHandler;

    @Before
    public void beforeTest()
    {
        mObservers = new ArrayList<>();
        mCallbackHandler = new StoryCommentsRecyclerViewModelCallbackHandler(mObservers);
    }

    @After
    public void afterTest()
    {
        mObservers.clear();
    }

    @Test
    public void registerStoryCommentsRecyclerViewModelCallbackObserver() throws Exception
    {
        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(null);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),2);
    }

    @Test
    public void unregisterStoryCommentsRecyclerViewModelCallbackObserver() throws Exception
    {
        mCallbackHandler.unregisterStoryCommentsRecyclerViewModelCallbackObserver(null);
        assertEquals(mObservers.size(),0);

        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(mObserverA);
        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(mObserverB);

        mCallbackHandler.unregisterStoryCommentsRecyclerViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterStoryCommentsRecyclerViewModelCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterStoryCommentsRecyclerViewModelCallbackObserver(null);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterStoryCommentsRecyclerViewModelCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),0);
    }


    @Test
    public void notifyLoadMoreStoryComments() throws Exception
    {
        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(mObserverA);
        mCallbackHandler.notifyLoadMoreStoryComments();
        Mockito.verify(mObserverA).loadMoreStoryComments();
    }

    @Test
    public void notifyStoryCommentClicked() throws Exception
    {

        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(mObserverA);
        StoryCommentBinderModel storyCommentBinderModel = Mockito.mock(StoryCommentBinderModel.class);

        mCallbackHandler.notifyStoryCommentClicked(storyCommentBinderModel);
        Mockito.verify(mObserverA).storyCommentClicked(storyCommentBinderModel);
    }

    @Test
    public void clear() throws Exception
    {
        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(mObserverA);
        mCallbackHandler.registerStoryCommentsRecyclerViewModelCallbackObserver(mObserverB);
        assertEquals(mObservers.size(), 2);
        mCallbackHandler.clear();
        assertEquals(mObservers.size(), 0);
    }


}