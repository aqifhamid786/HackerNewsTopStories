package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsApiManagerTopStoriesCallbackHandler extends IHackerNewsApiManagerTopStoriesCallbackObservable
{
    void notifyTopStoriesSuccess(int[] storiesIds);
    void notifyTopStoriesFailure(String message);
}
