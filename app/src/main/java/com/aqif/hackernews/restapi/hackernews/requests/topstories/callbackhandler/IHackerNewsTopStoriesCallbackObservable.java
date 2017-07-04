package com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsTopStoriesCallbackObservable
{
    void registerHackerNewsTopStoriesCallbackObserver(IHackerNewsTopStoriesCallbackObserver hackerNewsTopStoriesCallbackObserver);
    void unregisterHackerNewsTopStoriesCallbackObserver(IHackerNewsTopStoriesCallbackObserver hackerNewsTopStoriesCallbackObserver);
    void clear();
}
