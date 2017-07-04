package com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer;

/**
 * Created by aqifhamid.
 */

public interface ITopStoriesRefreshViewModelCallbackObservable
{
    void registerTopStoriesRefreshViewModelCallbackObserver(ITopStoriesRefreshViewModelCallbackObserver topStoriesRefreshViewModelCallbackObserver);
    void unregisterTopStoriesRefreshViewModelCallbackObserver(ITopStoriesRefreshViewModelCallbackObserver topStoriesRefreshViewModelCallbackObserver);

    void clear();

}
