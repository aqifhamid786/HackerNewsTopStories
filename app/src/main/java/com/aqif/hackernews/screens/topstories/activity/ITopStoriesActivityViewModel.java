package com.aqif.hackernews.screens.topstories.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by aqifhamid.
 */

public interface ITopStoriesActivityViewModel
{
    void onActivityCreateCalled(AppCompatActivity activity);
    void onActivityStartCalled();
    void onActivityResumeCalled();
    void onActivityPauseCalled();
    void onActiviyStopCalled();
    void onActivityDestroyCalled();
    boolean onBackPressed();
}
