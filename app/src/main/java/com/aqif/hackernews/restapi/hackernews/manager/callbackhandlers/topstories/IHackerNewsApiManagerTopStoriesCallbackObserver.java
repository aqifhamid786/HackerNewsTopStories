package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsApiManagerTopStoriesCallbackObserver
{
    void onTopStoriesSuccess(int[] storiesIds);
    void onTopStoriesFailure(String message);
}
