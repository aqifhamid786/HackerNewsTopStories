package com.aqif.hackernews.screens.topstories.refresh.dagger;

import android.support.v4.widget.SwipeRefreshLayout;

import com.aqif.hackernews.screens.topstories.activity.dagger.scope.TopStoriesActivityScope;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.ITopStoriesRefreshViewModel;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.TopStoriesRefreshViewModel;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackHandler;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackObservable;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackObserver;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.TopStoriesRefreshViewModelCallbackHandler;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aqifhamid.
 */

@Module
public class TopStoriesRefreshLayoutModule
{

    @Provides
    @TopStoriesActivityScope
    public ITopStoriesRefreshViewModelCallbackHandler provideTopStoriesRefreshViewModelCallbackHandler()
    {
        return new TopStoriesRefreshViewModelCallbackHandler(new ArrayList<ITopStoriesRefreshViewModelCallbackObserver>());
    }


    @Provides
    @TopStoriesActivityScope
    public ITopStoriesRefreshViewModel getTopStoriesRefreshViewModel(SwipeRefreshLayout swipeSwipeRefreshLayout, ITopStoriesRefreshViewModelCallbackHandler topStoriesRefreshViewModelCallbackHandler)
    {
        return new TopStoriesRefreshViewModel(swipeSwipeRefreshLayout, topStoriesRefreshViewModelCallbackHandler);
    }
}
