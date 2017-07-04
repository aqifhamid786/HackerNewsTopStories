package com.aqif.hackernews.restapi.hackernews.requests.topstories.dagger;


import com.aqif.hackernews.restapi.dagger.RestApiModule;
import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.IHackerNewsTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler.IHackerNewsTopStoriesRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler.IHackerNewsTopStoriesRetrofitService;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@RestApiScope
@Component(modules = {HackerNewsTopStoriesApiModule.class, RestApiModule.class})
public interface HackerNewsTopStoriesApiComponent
{
    IHackerNewsTopStoriesCallbackHandler provideHackerNewsTopStoriesCallbackHandler();
    IHackerNewsTopStoriesRetrofitService provideHackerNewsTopStoriesRetrofitService();
    IHackerNewsTopStoriesRequestHandler provideHackerNewsTopStoriesRequestHandler();
}
