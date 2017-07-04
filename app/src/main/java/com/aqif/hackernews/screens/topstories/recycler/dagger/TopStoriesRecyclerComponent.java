package com.aqif.hackernews.screens.topstories.recycler.dagger;

import android.support.v7.widget.LinearLayoutManager;

import com.aqif.hackernews.recycler.observer.RecyclerViewScrollToEndObserver;
import com.aqif.hackernews.screens.topstories.activity.dagger.TopStoriesActivityModule;
import com.aqif.hackernews.screens.topstories.activity.dagger.scope.TopStoriesActivityScope;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoriesAdapter;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.ITopStoriesRecyclerViewModel;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackHandler;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackObservable;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@TopStoriesActivityScope
@Component(modules = {TopStoriesRecyclerModule.class, TopStoriesActivityModule.class})
public interface TopStoriesRecyclerComponent
{
    ITopStoriesRecyclerViewModelCallbackHandler provideTopStoriesRecyclerViewModelCallbackHandler();
    LinearLayoutManager provideLinearLayoutManager();
    RecyclerViewScrollToEndObserver provideRecyclerViewScrollToEndObserver();
    TopStoriesAdapter provideTopStoriesAdapter();
    ITopStoriesRecyclerViewModel provideTopStoriesRecyclerViewModel();
}
