package com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer;

import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by aqifhamid.
 */

public class TopStoriesRecyclerViewModelCallbackHandler implements ITopStoriesRecyclerViewModelCallbackHandler
{

    protected List<ITopStoriesRecyclerViewModelCallbackObserver> mTopStoriesRecyclerViewModelCallbackObservers;

    @Inject
    public TopStoriesRecyclerViewModelCallbackHandler(List<ITopStoriesRecyclerViewModelCallbackObserver> topStoriesRecyclerViewModelCallbackObservers)
    {
        mTopStoriesRecyclerViewModelCallbackObservers = topStoriesRecyclerViewModelCallbackObservers;
    }

    @Override
    public void registerTopStoriesRecyclerViewModelCallbackObserver(ITopStoriesRecyclerViewModelCallbackObserver topStoriesRecyclerViewModelCallbackObserver)
    {
        if(topStoriesRecyclerViewModelCallbackObserver!=null &&
                !mTopStoriesRecyclerViewModelCallbackObservers.contains(topStoriesRecyclerViewModelCallbackObserver))
        {
            mTopStoriesRecyclerViewModelCallbackObservers.add(topStoriesRecyclerViewModelCallbackObserver);
        }
    }

    @Override
    public void unregisterTopStoriesRecyclerViewModelCallbackObserver(ITopStoriesRecyclerViewModelCallbackObserver topStoriesRecyclerViewModelCallbackObserver)
    {
        if(topStoriesRecyclerViewModelCallbackObserver!=null &&
                mTopStoriesRecyclerViewModelCallbackObservers.contains(topStoriesRecyclerViewModelCallbackObserver))
        {
            mTopStoriesRecyclerViewModelCallbackObservers.remove(topStoriesRecyclerViewModelCallbackObserver);
        }
    }

    @Override
    public void notifyLoadMoreTopStories()
    {
        for(int lop=0; lop<mTopStoriesRecyclerViewModelCallbackObservers.size(); lop++)
        {
            mTopStoriesRecyclerViewModelCallbackObservers.get(lop).loadMoreTopStories();
        }
    }

    @Override
    public void notifyTopStoryClicked(TopStoryBinderModel topStoryBinderModel)
    {
        for(int lop=0; lop<mTopStoriesRecyclerViewModelCallbackObservers.size(); lop++)
        {
            mTopStoriesRecyclerViewModelCallbackObservers.get(lop).topStoryClicked(topStoryBinderModel);
        }
    }

    @Override
    public void clear()
    {
        mTopStoriesRecyclerViewModelCallbackObservers.clear();
    }


}
