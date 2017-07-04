package com.aqif.hackernews.screens.topstories.activity.dagger;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.aqif.hackernews.restapi.hackernews.manager.IHackerNewsApiManager;
import com.aqif.hackernews.screens.topstories.activity.ITopStoriesActivityViewModel;
import com.aqif.hackernews.screens.topstories.activity.TopStoriesActivityViewModel;
import com.aqif.hackernews.screens.topstories.activity.dagger.scope.TopStoriesActivityScope;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.ITopStoriesRecyclerViewModel;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.ITopStoriesRefreshViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aqifhamid.
 */

@Module
public class TopStoriesViewModelModule
{

    @Provides
    @TopStoriesActivityScope
    public ITopStoriesActivityViewModel provideTopStoriesActivityViewModel(AppCompatActivity activity,
                                                                           Toolbar toolbar,
                                                                           TextView textView,
                                                                           ITopStoriesRecyclerViewModel topStoriesRecyclerViewModel,
                                                                           ITopStoriesRefreshViewModel topStoriesRefreshViewModel,
                                                                           IHackerNewsApiManager hackerNewsApiManager)


    {
        return new TopStoriesActivityViewModel(activity,
                toolbar,
                textView,
                topStoriesRecyclerViewModel,
                topStoriesRefreshViewModel,
                hackerNewsApiManager);
    }
}
