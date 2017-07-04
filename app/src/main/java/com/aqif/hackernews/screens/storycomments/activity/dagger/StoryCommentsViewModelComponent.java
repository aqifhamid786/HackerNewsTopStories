package com.aqif.hackernews.screens.storycomments.activity.dagger;

import com.aqif.hackernews.restapi.hackernews.manager.dagger.HackerNewsApiManagerComponent;
import com.aqif.hackernews.screens.storycomments.activity.IStoryCommentsActivityViewModel;
import com.aqif.hackernews.screens.storycomments.activity.StoryCommentsActivity;
import com.aqif.hackernews.screens.storycomments.activity.dagger.scope.StoryCommentsActivityScope;
import com.aqif.hackernews.screens.storycomments.recycler.dagger.StoryCommentsRecyclerModule;
import com.aqif.hackernews.screens.topstories.refresh.dagger.TopStoriesRefreshLayoutModule;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@StoryCommentsActivityScope
@Component(modules = {StoryCommentsViewModelModule.class,  // activity module for activity related references
        StoryCommentsActivityModule.class,
        StoryCommentsRecyclerModule.class
}, dependencies = {HackerNewsApiManagerComponent.class})
public interface StoryCommentsViewModelComponent
{
    void inject(StoryCommentsActivity activty);

    IStoryCommentsActivityViewModel provideStoryCommentsActivityViewModel();
}
