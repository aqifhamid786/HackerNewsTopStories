package com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemCallbackObservable 
{
    void registerHackerNewsItemCallbackObserver(IHackerNewsItemCallbackObserver hackerNewsItemCallbackObserver);
    void unregisterHackerNewsItemCallbackObserver(IHackerNewsItemCallbackObserver hackerNewsItemCallbackObserver);
    void clear();
}
