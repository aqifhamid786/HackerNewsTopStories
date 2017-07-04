package com.aqif.hackernews.screens.storycomments.activity.dagger;

import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aqif.hackernews.restapi.hackernews.manager.IHackerNewsApiManager;
import com.aqif.hackernews.screens.storycomments.activity.IStoryCommentsActivityViewModel;
import com.aqif.hackernews.screens.storycomments.activity.StoryCommentsActivityViewModel;
import com.aqif.hackernews.screens.storycomments.activity.dagger.qualifiers.EmptyListTextView;
import com.aqif.hackernews.screens.storycomments.activity.dagger.qualifiers.TopStoryTitleTextView;
import com.aqif.hackernews.screens.storycomments.activity.dagger.scope.StoryCommentsActivityScope;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.IStoryCommentsRecyclerViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aqifhamid.
 */

@Module
public class StoryCommentsViewModelModule
{

    @Provides
    @StoryCommentsActivityScope
    public IStoryCommentsActivityViewModel provideStoryCommentsActivityViewModel(Toolbar toolbar,
                                                                                 ProgressBar progressBar,
                                                                                 @EmptyListTextView TextView emptylistTextView,
                                                                                 @TopStoryTitleTextView TextView topStoryTitleTextView,
                                                                                 IStoryCommentsRecyclerViewModel storyCommentsRecyclerViewModel,
                                                                                 IHackerNewsApiManager hackerNewsApiManager)


    {



        return new StoryCommentsActivityViewModel(toolbar,
                progressBar,
                emptylistTextView,
                topStoryTitleTextView,
                storyCommentsRecyclerViewModel,
                hackerNewsApiManager);
    }
}
