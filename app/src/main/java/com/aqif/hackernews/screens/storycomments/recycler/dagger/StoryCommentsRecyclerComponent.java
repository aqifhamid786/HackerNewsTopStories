package com.aqif.hackernews.screens.storycomments.recycler.dagger;

import android.support.v7.widget.LinearLayoutManager;

import com.aqif.hackernews.recycler.observer.RecyclerViewScrollToEndObserver;
import com.aqif.hackernews.screens.storycomments.activity.dagger.StoryCommentsActivityModule;
import com.aqif.hackernews.screens.storycomments.activity.dagger.scope.StoryCommentsActivityScope;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentsAdapter;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.IStoryCommentsRecyclerViewModel;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackHandler;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackObservable;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@StoryCommentsActivityScope
@Component(modules = {StoryCommentsRecyclerModule.class, StoryCommentsActivityModule.class})
public interface StoryCommentsRecyclerComponent
{
    IStoryCommentsRecyclerViewModelCallbackHandler provideStoryCommentsRecyclerViewModelCallbackHandler();
    LinearLayoutManager provideLinearLayoutManager();
    RecyclerViewScrollToEndObserver provideRecyclerViewScrollToEndObserver();
    StoryCommentsAdapter provideStoryCommentsAdapter();
    IStoryCommentsRecyclerViewModel provideStoryCommentsRecyclerViewModel();

}
