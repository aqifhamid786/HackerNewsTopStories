package com.aqif.hackernews.restapi.hackernews.manager.dagger;

import com.aqif.hackernews.restapi.dagger.RestApiModule;
import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.dagger.HackerNewsItemsBatchProcessorModule;
import com.aqif.hackernews.restapi.hackernews.manager.IHackerNewsApiManager;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsCallbackBatchProcessorObservable;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackObservable;
import com.aqif.hackernews.restapi.hackernews.requests.item.dagger.HackerNewsItemApiModule;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.dagger.HackerNewsTopStoriesApiModule;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler.IHackerNewsTopStoriesRequestHandler;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@RestApiScope
@Component(modules = {HackerNewsApiManagerModule.class,
        HackerNewsItemApiModule.class, HackerNewsTopStoriesApiModule.class, // request handler modules
        HackerNewsItemsBatchProcessorModule.class, // Items batcher
        RestApiModule.class     // rest api module
})
public interface HackerNewsApiManagerComponent
{
    IHackerNewsApiManagerItemsBatchProcessorCallbackHandler provideHackerNewsApiManagerItemsBatchProcessorCallbackHandler();
    IHackerNewsApiManagerTopStoriesCallbackHandler provideHackerNewsApiManagerTopStoriesCallbackHandler();
    IHackerNewsApiManager provideHackerNewsApiManager();
}
