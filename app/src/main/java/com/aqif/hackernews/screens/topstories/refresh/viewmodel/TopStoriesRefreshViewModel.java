package com.aqif.hackernews.screens.topstories.refresh.viewmodel;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;

import com.aqif.hackernews.R;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackHandler;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackObservable;

/**
 * Created by aqifhamid.
 */

public class TopStoriesRefreshViewModel implements ITopStoriesRefreshViewModel,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ITopStoriesRefreshViewModelCallbackHandler mTopStoriesRefreshViewModelCallbackHandler;

    public TopStoriesRefreshViewModel(SwipeRefreshLayout swipeSwipeRefreshLayout,
                                      ITopStoriesRefreshViewModelCallbackHandler topStoriesRefreshViewModelCallbackHandler)
    {
        mSwipeRefreshLayout = swipeSwipeRefreshLayout;
        mTopStoriesRefreshViewModelCallbackHandler = topStoriesRefreshViewModelCallbackHandler;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorLoaderSchemeA,
                R.color.colorLoaderSchemeB);

    }

    @Override
    public void showLoader()
    {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        }, 100);
    }

    @Override
    public void hideLoader()
    {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 100);
    }

    @Override
    public ITopStoriesRefreshViewModelCallbackObservable getTopStoriesRefreshViewModelObservable()
    {
        return mTopStoriesRefreshViewModelCallbackHandler;
    }

    @Override
    public void onRefresh()
    {
        mTopStoriesRefreshViewModelCallbackHandler.notifyRefresh();
    }

    @Override
    public void onActivityDestroyCalled()
    {
        mTopStoriesRefreshViewModelCallbackHandler.clear();
        mTopStoriesRefreshViewModelCallbackHandler = null;
        mSwipeRefreshLayout.setOnRefreshListener(null);
        mSwipeRefreshLayout = null;
    }
}
