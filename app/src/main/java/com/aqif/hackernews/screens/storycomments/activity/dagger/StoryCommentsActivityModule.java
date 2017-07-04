package com.aqif.hackernews.screens.storycomments.activity.dagger;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aqif.hackernews.R;
import com.aqif.hackernews.screens.storycomments.activity.dagger.qualifiers.EmptyListTextView;
import com.aqif.hackernews.screens.storycomments.activity.dagger.qualifiers.TopStoryTitleTextView;
import com.aqif.hackernews.screens.storycomments.activity.dagger.scope.StoryCommentsActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aqifhamid.
 */

@Module
public class StoryCommentsActivityModule
{
    private AppCompatActivity mActivity;

    public StoryCommentsActivityModule(AppCompatActivity activity)
    {
        mActivity = activity;
    }

    @Provides
    @StoryCommentsActivityScope
    AppCompatActivity provideAppCompatActivity()
    {
        return mActivity;
    }

    @Provides
    @StoryCommentsActivityScope
    RecyclerView provideRecyclerView()
    {
        return (RecyclerView) mActivity.findViewById(R.id.recycler_view_story_comments);
    }

    @Provides
    @StoryCommentsActivityScope
    Toolbar provideToolbar()
    {
        return (Toolbar) mActivity.findViewById(R.id.toolbar);
    }

    @Provides
    @StoryCommentsActivityScope
    ProgressBar provideProgressBar()
    {
        return (ProgressBar) mActivity.findViewById(R.id.loading_progress);
    }

    @Provides
    @EmptyListTextView
    @StoryCommentsActivityScope
    TextView provideEmptyListTextView()
    {
        return (TextView) mActivity.findViewById(R.id.empty_list_message);
    }

    @Provides
    @TopStoryTitleTextView
    @StoryCommentsActivityScope
    TextView provideTopStoryTitleTextView()
    {
        return (TextView) mActivity.findViewById(R.id.top_story_title);
    }

}
