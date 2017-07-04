package com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.factory;

import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.factory.IHackerNewsItemCallbackHandlerFactory;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.HackerNewsItemRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRetrofitService;

import dagger.Provides;

/**
 * Created by aqifhamid.
 */

public class HackerNewsItemRequestHandlerFactory implements IHackerNewsItemRequestHandlerFactory
{

    private IHackerNewsItemRetrofitService mHackerNewsItemRetrofitService;
    private IHackerNewsItemCallbackHandlerFactory mHackerNewsItemCallbackHandlerFactory;

    public HackerNewsItemRequestHandlerFactory(IHackerNewsItemRetrofitService hackerNewsItemRetrofitService, IHackerNewsItemCallbackHandlerFactory hackerNewsItemCallbackHandlerFactory)
    {
        mHackerNewsItemRetrofitService = hackerNewsItemRetrofitService;
        mHackerNewsItemCallbackHandlerFactory = hackerNewsItemCallbackHandlerFactory;
    }

    @Override
    public IHackerNewsItemRequestHandler getHackerNewsItemRequestHandlerInstance()
    {
        return new HackerNewsItemRequestHandler(mHackerNewsItemRetrofitService, mHackerNewsItemCallbackHandlerFactory.getHackerNewsItemCallbackHandlerInstance());
    }
}
