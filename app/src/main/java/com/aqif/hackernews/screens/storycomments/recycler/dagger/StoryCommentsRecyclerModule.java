package com.aqif.hackernews.screens.storycomments.recycler.dagger;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aqif.hackernews.recycler.observer.RecyclerViewScrollToEndObserver;
import com.aqif.hackernews.screens.storycomments.activity.dagger.scope.StoryCommentsActivityScope;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentsAdapter;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.IStoryCommentsRecyclerViewModel;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.StoryCommentsRecyclerViewModel;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackHandler;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackObservable;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackObserver;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.StoryCommentsRecyclerViewModelCallbackHandler;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aqifhamid.
 */

@Module
public class StoryCommentsRecyclerModule
{

    @Provides
    @StoryCommentsActivityScope
    public IStoryCommentsRecyclerViewModelCallbackHandler provideStoryCommentsRecyclerViewModelCallbackHandler()
    {
        return new StoryCommentsRecyclerViewModelCallbackHandler(new ArrayList<IStoryCommentsRecyclerViewModelCallbackObserver>());
    }

    @Provides
    @StoryCommentsActivityScope
    public LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity)
    {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @StoryCommentsActivityScope
    public RecyclerViewScrollToEndObserver provideRecyclerViewScrollToEndObserver(LinearLayoutManager linearLayoutManager)
    {
        return new RecyclerViewScrollToEndObserver(linearLayoutManager);
    }

    @Provides
    @StoryCommentsActivityScope
    public StoryCommentsAdapter provideStoryCommentsAdapter(AppCompatActivity activity)
    {
        return new StoryCommentsAdapter(activity.getLayoutInflater());
    }

    @Provides
    @StoryCommentsActivityScope
    public IStoryCommentsRecyclerViewModel provideStoryCommentsRecyclerViewModel(LinearLayoutManager linearLayoutManager,
                                                                              RecyclerView recyclerView,
                                                                              StoryCommentsAdapter storyCommentsAdapter,
                                                                                 IStoryCommentsRecyclerViewModelCallbackHandler storyCommentsRecyclerViewModelCallbackHandler,
                                                                              RecyclerViewScrollToEndObserver recyclerViewScrollToEndObserver)
    {

        recyclerView.setLayoutManager(linearLayoutManager);
        return new StoryCommentsRecyclerViewModel(recyclerView, storyCommentsAdapter, storyCommentsRecyclerViewModelCallbackHandler, recyclerViewScrollToEndObserver);
    }
}
