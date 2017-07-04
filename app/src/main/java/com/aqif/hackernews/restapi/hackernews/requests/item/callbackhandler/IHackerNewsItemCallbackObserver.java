package com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemCallbackObserver
{
    void onHackerNewsItemSuccess(ItemDao item, Object tag);
    void onHackerNewsItemFailed(String message, Object tag);
}
