package com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler;

import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackObservable;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemRequestHandler
{
    IHackerNewsItemCallbackObservable getHackerNewsItemCallbackObservable();
    void performHackerNewsItemGet(int itemId);
    void clear();
    void cancel();

    Object getTag();
    void setTag(Object tag);
}
