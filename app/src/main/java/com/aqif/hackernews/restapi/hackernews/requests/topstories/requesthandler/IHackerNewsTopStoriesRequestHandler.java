package com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler;

import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.IHackerNewsTopStoriesCallbackObservable;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsTopStoriesRequestHandler
{
    IHackerNewsTopStoriesCallbackObservable getHackerNewsTopStoriesCallbackObservable();
    void performHackerNewsTopStoriesGet();
    void cancel();
    void clear();

    Object getTag();
    void setTag(Object tag);
}
