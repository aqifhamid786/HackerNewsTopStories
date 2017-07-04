package com.aqif.hackernews.screens.storycomments.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqif.hackernews.R;
import com.aqif.hackernews.restapi.dagger.RestApiModule;
import com.aqif.hackernews.restapi.hackernews.HackerNewsConstants;
import com.aqif.hackernews.restapi.hackernews.manager.dagger.DaggerHackerNewsApiManagerComponent;
import com.aqif.hackernews.restapi.hackernews.manager.dagger.HackerNewsApiManagerComponent;
import com.aqif.hackernews.screens.storycomments.activity.dagger.DaggerStoryCommentsViewModelComponent;
import com.aqif.hackernews.screens.storycomments.activity.dagger.StoryCommentsActivityModule;
import com.aqif.hackernews.screens.storycomments.activity.dagger.StoryCommentsViewModelComponent;

import javax.inject.Inject;

/**
 * Created by aqifhamid.
 */

public class StoryCommentsActivity extends AppCompatActivity
{

    @Inject public IStoryCommentsActivityViewModel mStoryCommentsActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_comments_activity);

        HackerNewsApiManagerComponent hackerNewsApiManagerComponent = DaggerHackerNewsApiManagerComponent.builder()
                .restApiModule(new RestApiModule(HackerNewsConstants.HackerNewsBaseURL))
                .build();

        StoryCommentsViewModelComponent daggerComponent = DaggerStoryCommentsViewModelComponent.builder()
                .storyCommentsActivityModule(new StoryCommentsActivityModule(this))
                .hackerNewsApiManagerComponent(hackerNewsApiManagerComponent)
                .build();

        daggerComponent.inject(this);
        mStoryCommentsActivityViewModel.onActivityCreateCalled(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mStoryCommentsActivityViewModel.onActivityStartCalled();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mStoryCommentsActivityViewModel.onActivityResumeCalled();
    }

    @Override
    protected void onPause()
    {
        mStoryCommentsActivityViewModel.onActivityPauseCalled();
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        mStoryCommentsActivityViewModel.onActiviyStopCalled();
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        mStoryCommentsActivityViewModel.onActivityDestroyCalled();
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        if(!mStoryCommentsActivityViewModel.onBackPressed())
        {
            super.onBackPressed();
        }
    }

}
