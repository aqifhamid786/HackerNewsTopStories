package com.aqif.hackernews.restapi.hackernews.requests.item.dagger;

import com.aqif.hackernews.restapi.dagger.RestApiModule;
import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.factory.IHackerNewsItemCallbackHandlerFactory;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRetrofitService;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.factory.IHackerNewsItemRequestHandlerFactory;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@RestApiScope
@Component(modules = {HackerNewsItemApiModule.class, RestApiModule.class})
public interface HackerNewsItemApiComponent
{
    IHackerNewsItemCallbackHandler provideHackerNewsItemCallbackHandler();
    IHackerNewsItemRetrofitService provideHackerNewsItemRetrofitService();
    IHackerNewsItemRequestHandlerFactory provideHackerNewsItemRequestHandlerFactory();
    IHackerNewsItemCallbackHandlerFactory provideHackerNewsItemCallbackHandlerFactory();
    IHackerNewsItemRequestHandler provideHackerNewsItemRequestHandler();
}
