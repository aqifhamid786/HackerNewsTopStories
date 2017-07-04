package com.aqif.hackernews.screens.topstories.refresh.dagger;

import com.aqif.hackernews.screens.topstories.activity.dagger.TopStoriesActivityModule;
import com.aqif.hackernews.screens.topstories.activity.dagger.scope.TopStoriesActivityScope;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.ITopStoriesRefreshViewModel;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackHandler;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackObservable;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@TopStoriesActivityScope
@Component(modules = {TopStoriesRefreshLayoutModule.class, TopStoriesActivityModule.class})
public interface TopStoriesRefreshLayoutComponent
{
    ITopStoriesRefreshViewModelCallbackHandler provideTopStoriesRefreshViewModelCallbackHandler();
    ITopStoriesRefreshViewModel getTopStoriesRefreshViewModel();
}
