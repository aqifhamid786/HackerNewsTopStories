package com.aqif.hackernews.restapi.hackernews.requests.topstories.dagger;

import com.aqif.hackernews.restapi.dagger.qualifiers.SuccessResponseRetrofit;
import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.HackerNewsTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.IHackerNewsTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.IHackerNewsTopStoriesCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler.HackerNewsTopStoriesRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler.IHackerNewsTopStoriesRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler.IHackerNewsTopStoriesRetrofitService;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by aqifhamid.
 */

@Module
public class HackerNewsTopStoriesApiModule
{

    @Provides
    @RestApiScope
    IHackerNewsTopStoriesCallbackHandler provideHackerNewsTopStoriesCallbackHandler()
    {
        return new HackerNewsTopStoriesCallbackHandler(new ArrayList<IHackerNewsTopStoriesCallbackObserver>());
    }

    @Provides
    @RestApiScope
    IHackerNewsTopStoriesRequestHandler provideHackerNewsTopStoriesRequestHandler(
            IHackerNewsTopStoriesRetrofitService hackerNewsTopStoriesRetrofitService
            , IHackerNewsTopStoriesCallbackHandler hackerNewsTopStoriesCallbackHandler)
    {
        return new HackerNewsTopStoriesRequestHandler(hackerNewsTopStoriesRetrofitService, hackerNewsTopStoriesCallbackHandler);
    }

    @Provides
    @RestApiScope
    IHackerNewsTopStoriesRetrofitService provideHackerNewsTopStoriesRetrofitService(@SuccessResponseRetrofit Retrofit retrofit)
    {
        return retrofit.create(IHackerNewsTopStoriesRetrofitService.class);
    }

}
