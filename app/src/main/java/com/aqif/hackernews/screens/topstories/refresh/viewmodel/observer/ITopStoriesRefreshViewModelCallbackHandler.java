package com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer;

/**
 * Created by aqifhamid.
 */

public interface ITopStoriesRefreshViewModelCallbackHandler extends ITopStoriesRefreshViewModelCallbackObservable
{
    void notifyRefresh();
}
