package com.aqif.hackernews.screens.topstories.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqif.hackernews.R;
import com.aqif.hackernews.restapi.dagger.RestApiModule;
import com.aqif.hackernews.restapi.hackernews.HackerNewsConstants;
import com.aqif.hackernews.restapi.hackernews.manager.dagger.DaggerHackerNewsApiManagerComponent;
import com.aqif.hackernews.restapi.hackernews.manager.dagger.HackerNewsApiManagerComponent;
import com.aqif.hackernews.screens.topstories.activity.dagger.DaggerTopStoriesViewModelComponent;
import com.aqif.hackernews.screens.topstories.activity.dagger.TopStoriesViewModelComponent;
import com.aqif.hackernews.screens.topstories.activity.dagger.TopStoriesActivityModule;

import javax.inject.Inject;

/**
 * Created by aqifhamid.
 */

public class TopStorieActivity  extends AppCompatActivity
{


    @Inject public ITopStoriesActivityViewModel mTopStoriesActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_stories_activity);

        HackerNewsApiManagerComponent hackerNewsApiManagerComponent = DaggerHackerNewsApiManagerComponent.builder()
                .restApiModule(new RestApiModule(HackerNewsConstants.HackerNewsBaseURL))
                .build();

        TopStoriesViewModelComponent daggerComponent = DaggerTopStoriesViewModelComponent.builder()
                .topStoriesActivityModule(new TopStoriesActivityModule(this))
                .hackerNewsApiManagerComponent(hackerNewsApiManagerComponent)
                .build();

        daggerComponent.inject(this);

        mTopStoriesActivityViewModel.onActivityCreateCalled(this);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mTopStoriesActivityViewModel.onActivityStartCalled();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mTopStoriesActivityViewModel.onActivityResumeCalled();
    }

    @Override
    protected void onPause()
    {

        mTopStoriesActivityViewModel.onActivityPauseCalled();
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        mTopStoriesActivityViewModel.onActiviyStopCalled();
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        mTopStoriesActivityViewModel.onActivityDestroyCalled();
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        if(!mTopStoriesActivityViewModel.onBackPressed())
        {
            super.onBackPressed();
        }
    }

}
