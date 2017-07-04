package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsApiManagerTopStoriesCallbackObservable
{
    void registerHackerNewsApiManagerTopStoriesCallbackObserver(
            IHackerNewsApiManagerTopStoriesCallbackObserver hackerNewsApiManagerTopStoriesCallbackObserver);
    void unregisterHackerNewsApiManagerTopStoriesCallbackObserver(
            IHackerNewsApiManagerTopStoriesCallbackObserver hackerNewsApiManagerTopStoriesCallbackObserver);

    void clear();
}
