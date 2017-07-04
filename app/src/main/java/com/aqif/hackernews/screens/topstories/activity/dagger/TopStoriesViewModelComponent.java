package com.aqif.hackernews.screens.topstories.activity.dagger;

import com.aqif.hackernews.restapi.hackernews.manager.dagger.HackerNewsApiManagerComponent;
import com.aqif.hackernews.screens.topstories.activity.ITopStoriesActivityViewModel;
import com.aqif.hackernews.screens.topstories.activity.TopStorieActivity;
import com.aqif.hackernews.screens.topstories.activity.dagger.scope.TopStoriesActivityScope;
import com.aqif.hackernews.screens.topstories.recycler.dagger.TopStoriesRecyclerModule;
import com.aqif.hackernews.screens.topstories.refresh.dagger.TopStoriesRefreshLayoutModule;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@TopStoriesActivityScope
@Component(modules = {TopStoriesViewModelModule.class,  // activity module for activity related references
        TopStoriesActivityModule.class,
        TopStoriesRecyclerModule.class,
        TopStoriesRefreshLayoutModule.class
}, dependencies = {HackerNewsApiManagerComponent.class})
public interface TopStoriesViewModelComponent
{
    void inject(TopStorieActivity activty);

    ITopStoriesActivityViewModel provideTopStoriesActivityViewModel();
}
