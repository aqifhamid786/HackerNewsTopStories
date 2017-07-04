package com.aqif.hackernews.screens.topstories.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.aqif.hackernews.restapi.hackernews.manager.IHackerNewsApiManager;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.ITopStoriesRecyclerViewModel;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackHandler;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.ITopStoriesRefreshViewModel;
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

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by aqifhamid.
 */
@RunWith(MockitoJUnitRunner.class)
public class TopStoriesActivityViewModelTest
{
    @Mock private AppCompatActivity mActivity;
    @Mock private Toolbar mToolbar;
    @Mock private TextView mTextView;
    @Mock private ITopStoriesRecyclerViewModel mTopStoriesRecyclerViewModel;
    @Mock private ITopStoriesRefreshViewModel mTopStoriesRefreshViewModel;
    @Mock private IHackerNewsApiManager mHackerNewsApiManager;

    private ArrayList<TopStoryBinderModel> mTopStoryBinderModels;
    private TopStoriesActivityViewModel mTopStoriesActivityViewModel;

    @Before
    public void beforeTest() throws Exception
    {
        IHackerNewsApiManagerTopStoriesCallbackHandler hackerNewsApiManagerTopStoriesCallbackHandler =
                Mockito.mock(IHackerNewsApiManagerTopStoriesCallbackHandler.class);
        Mockito.when(mHackerNewsApiManager.getHackerNewsApiManagerTopStoriesObservable())
                .thenReturn(hackerNewsApiManagerTopStoriesCallbackHandler);

        IHackerNewsApiManagerItemsBatchProcessorCallbackHandler hackerNewsApiManagerItemsBatchProcessorCallbackHandler =
                Mockito.mock(IHackerNewsApiManagerItemsBatchProcessorCallbackHandler.class);
        Mockito.when(mHackerNewsApiManager.getHackerNewsApiManagerItemsBatchProcessorObservable())
                .thenReturn(hackerNewsApiManagerItemsBatchProcessorCallbackHandler);

        ITopStoriesRecyclerViewModelCallbackHandler topStoriesRecyclerViewModelCallbackHandler =
                Mockito.mock(ITopStoriesRecyclerViewModelCallbackHandler.class);
        Mockito.when(mTopStoriesRecyclerViewModel.getTopStoriesRecyclerViewModelObserver())
                .thenReturn(topStoriesRecyclerViewModelCallbackHandler);

        ITopStoriesRefreshViewModelCallbackHandler topStoriesRefreshViewModelCallbackHandler =
                Mockito.mock(ITopStoriesRefreshViewModelCallbackHandler.class);
        Mockito.when(mTopStoriesRefreshViewModel.getTopStoriesRefreshViewModelObservable())
                .thenReturn(topStoriesRefreshViewModelCallbackHandler);

        mTopStoryBinderModels = Mockito.spy(new ArrayList<TopStoryBinderModel>());
        mTopStoriesActivityViewModel = new TopStoriesActivityViewModel(mActivity,
                mToolbar,
                mTextView,
                mTopStoriesRecyclerViewModel,
                mTopStoriesRefreshViewModel,
                mHackerNewsApiManager,
                mTopStoryBinderModels);
    }

    @After
    public void afterTest() throws Exception
    {
        mTopStoryBinderModels.clear();
        mTopStoriesActivityViewModel = null;
    }

    @Test
    public void onActivityCreateCalled() throws Exception
    {
        mTopStoriesActivityViewModel.onActivityCreateCalled(mActivity);
        Mockito.verify(mHackerNewsApiManager).getHackerNewsApiManagerTopStoriesObservable();
        Mockito.verify(mTopStoriesRefreshViewModel).showLoader();
        Mockito.verify(mHackerNewsApiManager).fetchTopStories();
    }

    @Test
    public void onActivityDestroyCalled() throws Exception
    {
        mTopStoriesActivityViewModel.onActivityDestroyCalled();
        Mockito.verify(mHackerNewsApiManager).clear();
        Mockito.verify(mTopStoriesRecyclerViewModel).onActivityDestroyCalled();
        Mockito.verify(mTopStoriesRefreshViewModel).onActivityDestroyCalled();
    }

    @Test
    public void onBackPressed() throws Exception
    {
        assertEquals(mTopStoriesActivityViewModel.onBackPressed(), false);
    }

    @Test
    public void loadMoreTopStories() throws Exception
    {
        mTopStoriesActivityViewModel.setTopStoriesIds(new int[]{});
        mTopStoriesActivityViewModel.loadMoreTopStories();

        Mockito.verify(mTopStoryBinderModels).size();
        int ids[] = new int[]{1,2,3,4};
        mTopStoriesActivityViewModel.setTopStoriesIds(ids);
        mTopStoriesActivityViewModel.loadMoreTopStories();
        Mockito.verify(mTopStoryBinderModels, Mockito.times(4)).size(); // means condition for if is true.
    }

    @Test
    public void topStoryClicked() throws Exception { }

    @Test
    public void processNextBatchOfTopStories() throws Exception
    {
        mTopStoryBinderModels.add(Mockito.mock(TopStoryBinderModel.class));
        mTopStoryBinderModels.add(Mockito.mock(TopStoryBinderModel.class));

        int[] itemsIds = new int[]{1,2,3,4};
        mTopStoriesActivityViewModel.setTopStoriesIds(itemsIds);

        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                assertEquals(invocation.<int[]>getArgument(0).length, 2);
                assertEquals(invocation.<int[]>getArgument(0)[0], 3);
                assertEquals(invocation.<int[]>getArgument(0)[1], 4);
                return null;
            }
        }).when(mHackerNewsApiManager).fetchItemsInABatch(Mockito.<int[]>any(), Mockito.any());
        mTopStoriesActivityViewModel.processNextBatchOfTopStories();
    }

    @Test
    public void onRefresh() throws Exception
    {
        mTopStoriesActivityViewModel.setRefreshing(false);
        mTopStoriesActivityViewModel.onRefresh();
        assertEquals(mTopStoriesActivityViewModel.isRefreshing(), true);
        Mockito.verify(mHackerNewsApiManager).getHackerNewsApiManagerItemsBatchProcessorObservable();
        Mockito.verify(mHackerNewsApiManager).cancelAllRequests();
        Mockito.verify(mHackerNewsApiManager).fetchTopStories();
        Mockito.verify(mTopStoryBinderModels).clear();
        Mockito.verify(mTextView).setVisibility(View.VISIBLE);
    }

    @Test
    public void onTopStoriesSuccess() throws Exception
    {
        int[] ids = new int[3];
        mTopStoriesActivityViewModel.onTopStoriesSuccess(ids);
        assertEquals(mTopStoriesActivityViewModel.getTopStoriesIds(), ids);
        Mockito.verify(mHackerNewsApiManager).getHackerNewsApiManagerItemsBatchProcessorObservable();
    }

    @Test
    public void onTopStoriesFailure() throws Exception
    {
        String message = "message";
        mTopStoriesActivityViewModel.setRefreshing(false);
        mTopStoriesActivityViewModel.onTopStoriesFailure(message);

        Mockito.verify(mTextView).setText(Mockito.eq(message));
        Mockito.verify(mTextView).setVisibility(View.VISIBLE);
        Mockito.verify(mTopStoriesRecyclerViewModel).onLoadDataFailed();
        Mockito.verify(mTopStoriesRefreshViewModel, Mockito.times(0)).hideLoader();

        mTopStoriesActivityViewModel.setRefreshing(true);
        mTopStoriesActivityViewModel.onTopStoriesFailure(message);
        Mockito.verify(mTopStoriesRefreshViewModel).hideLoader();
    }

    @Test
    public void onItemsBatchSuccess() throws Exception
    {
        Object tag = new Object();
        ArrayList<ItemDao> items = new ArrayList<>();
        items.add(new ItemDao());
        mTopStoriesActivityViewModel.setRefreshing(false);
        mTopStoriesActivityViewModel.onItemsBatchSuccess(items, tag);
        assertEquals(mTopStoryBinderModels.size(), 1);

        Mockito.verify(mTopStoriesRecyclerViewModel).setRecyclerViewData(Mockito.eq(mTopStoryBinderModels), Mockito.anyBoolean());
        mTopStoriesActivityViewModel.setRefreshing(false);
        Mockito.verify(mTopStoriesRefreshViewModel, Mockito.times(0)).hideLoader();

        mTopStoriesActivityViewModel.setRefreshing(true);
        mTopStoriesActivityViewModel.onItemsBatchSuccess(items, tag);

        Mockito.verify(mTextView).setVisibility(View.INVISIBLE);
        Mockito.verify(mTopStoriesRefreshViewModel).hideLoader();
    }

    @Test
    public void onItemsBatchFailure() throws Exception
    {
        Object tag = new Object();
        String message = "message";

        mTopStoriesActivityViewModel.setRefreshing(false);
        mTopStoriesActivityViewModel.onItemsBatchFailure(message, tag);

        Mockito.verify(mTextView).setText(Mockito.eq(message));
        Mockito.verify(mTextView).setVisibility(View.VISIBLE);
        Mockito.verify(mTopStoriesRecyclerViewModel).setRecyclerViewData(Mockito.eq(mTopStoryBinderModels), Mockito.anyBoolean());
        Mockito.verify(mTopStoriesRecyclerViewModel).onLoadDataFailed();

        mTopStoryBinderModels.add(Mockito.mock(TopStoryBinderModel.class));

        mTopStoriesActivityViewModel.onItemsBatchFailure(message, tag);
        Mockito.verify(mTextView).setVisibility(View.INVISIBLE);
        Mockito.verify(mTopStoriesRefreshViewModel, Mockito.times(0)).hideLoader();

        mTopStoriesActivityViewModel.setRefreshing(true);
        mTopStoriesActivityViewModel.onItemsBatchFailure(message, tag);
        Mockito.verify(mTopStoriesRefreshViewModel).hideLoader();
    }

}