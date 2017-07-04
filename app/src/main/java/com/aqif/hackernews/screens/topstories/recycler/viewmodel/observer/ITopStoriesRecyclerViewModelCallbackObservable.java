package com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer;

import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;

/**
 * Created by aqifhamid.
 */
public interface ITopStoriesRecyclerViewModelCallbackObservable
{
    void registerTopStoriesRecyclerViewModelCallbackObserver(ITopStoriesRecyclerViewModelCallbackObserver topStoriesRecyclerViewModelCallbackObserver);
    void unregisterTopStoriesRecyclerViewModelCallbackObserver(ITopStoriesRecyclerViewModelCallbackObserver topStoriesRecyclerViewModelCallbackObserver);
    void clear();

}
