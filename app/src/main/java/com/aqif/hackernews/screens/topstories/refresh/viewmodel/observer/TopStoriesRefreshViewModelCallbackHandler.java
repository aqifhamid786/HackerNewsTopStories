package com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by aqifhamid.
 */

public class TopStoriesRefreshViewModelCallbackHandler implements ITopStoriesRefreshViewModelCallbackHandler
{

    protected List<ITopStoriesRefreshViewModelCallbackObserver> mTopStoriesRefreshViewModelCallbackObservers;

    @Inject
    public TopStoriesRefreshViewModelCallbackHandler(List<ITopStoriesRefreshViewModelCallbackObserver> topStoriesRefreshViewModelCallbackObservers)
    {
        mTopStoriesRefreshViewModelCallbackObservers = topStoriesRefreshViewModelCallbackObservers;
    }
    ;
    ;
    @Override
    public void registerTopStoriesRefreshViewModelCallbackObserver(ITopStoriesRefreshViewModelCallbackObserver topStoriesRefreshViewModelCallbackObserver)
    {
        if(topStoriesRefreshViewModelCallbackObserver!=null && !mTopStoriesRefreshViewModelCallbackObservers.contains(topStoriesRefreshViewModelCallbackObserver))
        {
            mTopStoriesRefreshViewModelCallbackObservers.add(topStoriesRefreshViewModelCallbackObserver);
        }
    }

    @Override
    public void unregisterTopStoriesRefreshViewModelCallbackObserver(ITopStoriesRefreshViewModelCallbackObserver topStoriesRefreshViewModelCallbackObserver)
    {
        if(topStoriesRefreshViewModelCallbackObserver!=null && mTopStoriesRefreshViewModelCallbackObservers.contains(topStoriesRefreshViewModelCallbackObserver))
        {
            mTopStoriesRefreshViewModelCallbackObservers.remove(topStoriesRefreshViewModelCallbackObserver);
        }
    }

    @Override
    public void notifyRefresh()
    {
        for(int lop=0; lop<mTopStoriesRefreshViewModelCallbackObservers.size(); lop++)
        {
            mTopStoriesRefreshViewModelCallbackObservers.get(lop).onRefresh();
        }
    }

    @Override
    public void clear()
    {
        mTopStoriesRefreshViewModelCallbackObservers.clear();
    }


}
