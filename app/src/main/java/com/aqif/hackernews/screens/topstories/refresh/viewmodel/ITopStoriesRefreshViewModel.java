package com.aqif.hackernews.screens.topstories.refresh.viewmodel;

import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackObservable;

/**
 * Created by aqifhamid.
 */

public interface ITopStoriesRefreshViewModel
{
    void hideLoader();
    void showLoader();

    void onActivityDestroyCalled();

    ITopStoriesRefreshViewModelCallbackObservable getTopStoriesRefreshViewModelObservable();
}
