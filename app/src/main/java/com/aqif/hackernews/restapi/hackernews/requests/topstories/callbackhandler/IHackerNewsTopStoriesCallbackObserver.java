package com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsTopStoriesCallbackObserver
{
    void onHackerNewsTopStoriesSuccess(int[] storiesIds, Object tag);
    void onHackerNewsTopStoriesFailed(String message, Object tag);
}
