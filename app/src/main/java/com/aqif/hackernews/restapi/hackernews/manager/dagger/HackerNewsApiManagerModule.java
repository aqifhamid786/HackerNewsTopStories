package com.aqif.hackernews.restapi.hackernews.manager.dagger;

import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.hackernews.manager.HackerNewsApiManager;
import com.aqif.hackernews.restapi.hackernews.manager.IHackerNewsApiManager;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.HackerNewsApiManagerItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsCallbackBatchProcessorObservable;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsBatchProcessorCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.HackerNewsApiManagerTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackObservable;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.IHackerNewsItemsBatchProcessor;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler.IHackerNewsTopStoriesRequestHandler;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aqifhamid.
 */

@Module
public class HackerNewsApiManagerModule
{
    @Provides
    @RestApiScope
    public IHackerNewsApiManagerItemsBatchProcessorCallbackHandler provideHackerNewsApiManagerItemsBatchProcessorCallbackHandler()
    {
        return new HackerNewsApiManagerItemsBatchProcessorCallbackHandler(new ArrayList<IHackerNewsApiManagerItemsBatchProcessorCallbackObserver>());
    }

    @Provides
    @RestApiScope
    public IHackerNewsApiManagerTopStoriesCallbackHandler provideHackerNewsApiManagerTopStoriesCallbackHandler()
    {
        return new HackerNewsApiManagerTopStoriesCallbackHandler(new ArrayList<IHackerNewsApiManagerTopStoriesCallbackObserver>());
    }

    @Provides
    @RestApiScope
    public IHackerNewsApiManager provideHackerNewsApiManager(IHackerNewsTopStoriesRequestHandler hackerNewsTopStoriesRequestHandler
            , IHackerNewsItemsBatchProcessor hackerNewsItemsBatchProcessor
            , IHackerNewsApiManagerTopStoriesCallbackHandler hackerNewsApiManagerTopStoriesCallbackHandler
            , IHackerNewsApiManagerItemsBatchProcessorCallbackHandler hackerNewsApiManagerItemsBatchProcessorCallbackHandler)
    {
        return new HackerNewsApiManager(hackerNewsTopStoriesRequestHandler,
                hackerNewsItemsBatchProcessor,
                hackerNewsApiManagerTopStoriesCallbackHandler,
                hackerNewsApiManagerItemsBatchProcessorCallbackHandler);
    }
}
