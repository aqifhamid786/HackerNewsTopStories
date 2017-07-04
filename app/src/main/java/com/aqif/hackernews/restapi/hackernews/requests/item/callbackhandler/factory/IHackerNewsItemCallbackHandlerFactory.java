package com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.factory;

import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRequestHandler;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemCallbackHandlerFactory
{
    IHackerNewsItemCallbackHandler getHackerNewsItemCallbackHandlerInstance();
}
