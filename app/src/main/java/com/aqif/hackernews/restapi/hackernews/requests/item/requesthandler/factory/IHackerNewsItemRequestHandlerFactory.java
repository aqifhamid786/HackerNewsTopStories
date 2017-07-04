package com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.factory;

import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRequestHandler;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemRequestHandlerFactory
{
    IHackerNewsItemRequestHandler getHackerNewsItemRequestHandlerInstance();
}
