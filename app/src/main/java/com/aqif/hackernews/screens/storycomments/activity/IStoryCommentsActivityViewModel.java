package com.aqif.hackernews.screens.storycomments.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by aqifhamid.
 */

public interface IStoryCommentsActivityViewModel
{
    void onActivityCreateCalled(AppCompatActivity activity);
    void onActivityStartCalled();
    void onActivityResumeCalled();
    void onActivityPauseCalled();
    void onActiviyStopCalled();
    void onActivityDestroyCalled();
    boolean onBackPressed();
}
