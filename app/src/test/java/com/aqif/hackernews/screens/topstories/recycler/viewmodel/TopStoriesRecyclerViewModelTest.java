package com.aqif.hackernews.screens.topstories.recycler.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.aqif.hackernews.recycler.observer.RecyclerViewScrollToEndObserver;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoriesAdapter;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryItem;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by aqifhamid.
 */


@RunWith(MockitoJUnitRunner.class)
public class TopStoriesRecyclerViewModelTest
{

    @Mock private RecyclerView mRecyclerView;
    @Mock private TopStoriesAdapter mTopStoriesAdapter;
    @Mock private ITopStoriesRecyclerViewModelCallbackHandler mTopStoriesRecyclerViewModelCallbackHandler;
    @Mock private RecyclerViewScrollToEndObserver mRecyclerViewScrollToEndObserver;

    private List<TopStoryItem> mTopStoryItems;
    private TopStoriesRecyclerViewModel mTopStoriesRecyclerViewModel;

    @Before
    public void beforeTest()
    {
        SimpleItemAnimator animator = Mockito.mock(SimpleItemAnimator.class);
        Mockito.when(mRecyclerView.getItemAnimator()).thenReturn(animator);

        mTopStoryItems = Mockito.spy(new ArrayList<TopStoryItem>());
        mTopStoriesRecyclerViewModel = new TopStoriesRecyclerViewModel(mRecyclerView, mTopStoriesAdapter, mTopStoriesRecyclerViewModelCallbackHandler,
                mRecyclerViewScrollToEndObserver, mTopStoryItems);
    }

    @After
    public void afterTest()
    {
        mTopStoryItems.clear();
        mTopStoriesRecyclerViewModel = null;
    }

    @Test
    public void getTopStoriesRecyclerViewModelObserver() throws Exception
    {
        assertNotNull(mTopStoriesRecyclerViewModelCallbackHandler);
        assertEquals(mTopStoriesRecyclerViewModelCallbackHandler, mTopStoriesRecyclerViewModel.getTopStoriesRecyclerViewModelObserver());
    }

    @Test
    public void onLoadDataFailed() throws Exception
    {
        mTopStoriesRecyclerViewModel.onLoadDataFailed();
        Mockito.verify(mTopStoryItems).remove(Mockito.<TopStoryItem>any());
        Mockito.verify(mTopStoriesAdapter).updateData(Mockito.eq(mTopStoryItems));
        Mockito.verify(mRecyclerViewScrollToEndObserver).setLoadingData(Mockito.anyBoolean());
    }

    @Test
    public void setRecyclerViewData() throws Exception
    {
        ArrayList<TopStoryBinderModel> topStoryBinderModels = new ArrayList<>();
        for(int lop=0; lop<5; lop++)
        {
            TopStoryBinderModel binderModel = Mockito.mock(TopStoryBinderModel.class);
            Mockito.when(binderModel.isDeleted()).thenReturn(false);
            topStoryBinderModels.add(binderModel);
        }

        mTopStoriesRecyclerViewModel.setRecyclerViewData(topStoryBinderModels, false);

        assertEquals(mTopStoryItems.size(), topStoryBinderModels.size());
        Mockito.verify(mTopStoriesAdapter).updateData(Mockito.eq(mTopStoryItems));
        Mockito.verify(mRecyclerViewScrollToEndObserver).setLoadingData(Mockito.eq(false));
    }

    @Test
    public void onLoadMoreRecycleViewDataListner() throws Exception
    {
        Mockito.when(mRecyclerView.post(Mockito.any(Runnable.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                invocation.<Runnable>getArgument(0).run();
                return null;
            }
        });

        mTopStoriesRecyclerViewModel.onLoadMoreRecycleViewDataListner(0, 1, mRecyclerView);
        assertEquals(mTopStoryItems.size(), 1);
        Mockito.verify(mTopStoriesAdapter).updateData(Mockito.eq(mTopStoryItems));
        Mockito.verify(mTopStoriesRecyclerViewModelCallbackHandler).notifyLoadMoreTopStories();

    }

    @Test
    public void onTopStoryViewItemClicked() throws Exception
    {
        TopStoryBinderModel topStoryBinderModel = Mockito.mock(TopStoryBinderModel.class);
        mTopStoriesRecyclerViewModel.onTopStoryViewItemClicked(topStoryBinderModel);
        Mockito.verify(mTopStoriesRecyclerViewModelCallbackHandler).notifyTopStoryClicked(Mockito.eq(topStoryBinderModel));
    }

    @Test
    public void onActivityDestroyCalled() throws Exception
    {
        mTopStoriesRecyclerViewModel.onActivityDestroyCalled();
        Mockito.verify(mTopStoryItems).remove(Mockito.any());
        Mockito.verify(mTopStoryItems).clear();

        Mockito.verify(mTopStoriesAdapter).updateData(Mockito.<List<TopStoryItem>>any());
        Mockito.verify(mRecyclerViewScrollToEndObserver).setOnLoadMoreRecycleViewDataListner(null);
    }

}