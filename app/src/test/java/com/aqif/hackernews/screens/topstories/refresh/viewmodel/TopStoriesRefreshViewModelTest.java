package com.aqif.hackernews.screens.topstories.refresh.viewmodel;

import android.support.v4.widget.SwipeRefreshLayout;

import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class TopStoriesRefreshViewModelTest
{

    private TopStoriesRefreshViewModel mTopStoriesRefreshViewModel;
    @Mock private SwipeRefreshLayout mSwipeSwipeRefreshLayoutMock;
    @Mock private ITopStoriesRefreshViewModelCallbackHandler mTopStoriesRefreshViewModelCallbackHandlerMock;

    @Before
    public void beforeTest()
    {
        mTopStoriesRefreshViewModel = new TopStoriesRefreshViewModel(mSwipeSwipeRefreshLayoutMock, mTopStoriesRefreshViewModelCallbackHandlerMock);
    }

    @After
    public void afterTest()
    {
    }

    @Test
    public void showLoader() throws Exception
    {

        Mockito.when(mSwipeSwipeRefreshLayoutMock.postDelayed(Mockito.any(Runnable.class), Mockito.anyLong())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                invocation.<Runnable>getArgument(0).run();
                return null;
            }
        });

        mTopStoriesRefreshViewModel.showLoader();
        Mockito.verify(mSwipeSwipeRefreshLayoutMock).postDelayed(Mockito.<Runnable>any(), Mockito.anyLong());
        Mockito.verify(mSwipeSwipeRefreshLayoutMock).setRefreshing(Mockito.eq(true));
    }

    @Test
    public void hideLoader() throws Exception
    {

        Mockito.when(mSwipeSwipeRefreshLayoutMock.postDelayed(Mockito.any(Runnable.class), Mockito.anyLong())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                invocation.<Runnable>getArgument(0).run();
                return null;
            }
        });

        mTopStoriesRefreshViewModel.hideLoader();
        Mockito.verify(mSwipeSwipeRefreshLayoutMock).postDelayed(Mockito.<Runnable>any(), Mockito.anyLong());
        Mockito.verify(mSwipeSwipeRefreshLayoutMock).setRefreshing(Mockito.eq(false));
    }

    @Test
    public void getTopStoriesRefreshViewModelObservable() throws Exception
    {
        assertNotNull(mTopStoriesRefreshViewModelCallbackHandlerMock);
        assertEquals(mTopStoriesRefreshViewModelCallbackHandlerMock, mTopStoriesRefreshViewModel.getTopStoriesRefreshViewModelObservable());
    }

    @Test
    public void onRefresh() throws Exception
    {
        mTopStoriesRefreshViewModel.onRefresh();
        Mockito.verify(mTopStoriesRefreshViewModelCallbackHandlerMock).notifyRefresh();
    }

    @Test
    public void onActivityDestroyCalled() throws Exception
    {
        mTopStoriesRefreshViewModel.onActivityDestroyCalled();
        Mockito.verify(mSwipeSwipeRefreshLayoutMock).setOnRefreshListener(null);
        Mockito.verify(mTopStoriesRefreshViewModelCallbackHandlerMock).clear();
    }

}