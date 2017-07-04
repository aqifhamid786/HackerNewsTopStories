package com.aqif.hackernews.restapi.hackernews.requests.item.dagger;

import com.aqif.hackernews.restapi.dagger.qualifiers.SuccessResponseRetrofit;
import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.HackerNewsItemCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.factory.HackerNewsItemCallbackHandlerFactory;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.factory.IHackerNewsItemCallbackHandlerFactory;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.HackerNewsItemRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRetrofitService;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.factory.HackerNewsItemRequestHandlerFactory;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.factory.IHackerNewsItemRequestHandlerFactory;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by aqifhamid.
 */

@Module
public class HackerNewsItemApiModule
{
    @Provides
    @RestApiScope
    IHackerNewsItemCallbackHandler provideHackerNewsItemCallbackHandler()
    {
        return new HackerNewsItemCallbackHandler(new ArrayList<IHackerNewsItemCallbackObserver>());
    }

    @Provides
    @RestApiScope
    IHackerNewsItemRetrofitService provideHackerNewsItemRetrofitService(@SuccessResponseRetrofit Retrofit retrofit)
    {
        return retrofit.create(IHackerNewsItemRetrofitService.class);
    }

    @Provides
    @RestApiScope
    IHackerNewsItemCallbackHandlerFactory provideHackerNewsItemCallbackHandlerFactory()
    {
        return new HackerNewsItemCallbackHandlerFactory();
    }

    @Provides
    @RestApiScope
    IHackerNewsItemRequestHandlerFactory provideHackerNewsItemRequestHandlerFactory(
            IHackerNewsItemRetrofitService hackerNewsItemRetrofitService
            , IHackerNewsItemCallbackHandlerFactory hackerNewsItemCallbackHandlerFactory)
    {
        return new HackerNewsItemRequestHandlerFactory(hackerNewsItemRetrofitService, hackerNewsItemCallbackHandlerFactory);
    }

    @Provides
    @RestApiScope
    IHackerNewsItemRequestHandler provideHackerNewsItemRequestHandler(
            IHackerNewsItemRetrofitService hackerNewsItemRetrofitService
            , IHackerNewsItemCallbackHandler hackerNewsItemCallbackHandler)
    {
        return new HackerNewsItemRequestHandler(hackerNewsItemRetrofitService, hackerNewsItemCallbackHandler);
    }

}
