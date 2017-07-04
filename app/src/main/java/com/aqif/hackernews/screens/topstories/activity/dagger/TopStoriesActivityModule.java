package com.aqif.hackernews.screens.topstories.activity.dagger;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.aqif.hackernews.R;
import com.aqif.hackernews.screens.topstories.activity.dagger.scope.TopStoriesActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aqifhamid.
 */

@Module
public class TopStoriesActivityModule
{
    private AppCompatActivity mActivity;

    public TopStoriesActivityModule(AppCompatActivity activity)
    {
        mActivity = activity;
    }

    @Provides
    @TopStoriesActivityScope
    AppCompatActivity provideAppCompatActivity()
    {
        return mActivity;
    }

    @Provides
    @TopStoriesActivityScope
    RecyclerView provideRecyclerView()
    {
        return (RecyclerView) mActivity.findViewById(R.id.recycler_view_top_stories);
    }

    @Provides
    @TopStoriesActivityScope
    SwipeRefreshLayout provideSwipeRefreshLayout()
    {
        return (SwipeRefreshLayout) mActivity.findViewById(R.id.swipe_refresh_layout_top_stories);
    }

    @Provides
    @TopStoriesActivityScope
    Toolbar provideToolbar()
    {
        return (Toolbar) mActivity.findViewById(R.id.toolbar);
    }

    @Provides
    @TopStoriesActivityScope
    TextView provideTextView()
    {
        return (TextView) mActivity.findViewById(R.id.empty_list_message);
    }

}
