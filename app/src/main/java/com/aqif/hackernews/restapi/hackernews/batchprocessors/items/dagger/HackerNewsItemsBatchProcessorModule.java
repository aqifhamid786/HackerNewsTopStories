package com.aqif.hackernews.restapi.hackernews.batchprocessors.items.dagger;

import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.HackerNewsItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.factory.IHackerNewsItemRequestHandlerFactory;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchProcessor;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.IHackerNewsItemsBatchProcessor;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackObservable;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aqifhamid.
 */

@Module
public class HackerNewsItemsBatchProcessorModule
{

    @Provides
    @RestApiScope
    public IHackerNewsItemsBatchProcessorCallbackHandler provideHackerNewsItemsBatchProcessorCallbackHandler()
    {
        return new HackerNewsItemsBatchProcessorCallbackHandler(new ArrayList<IHackerNewsItemsBatchProcessorCallbackObserver>());
    }

    @Provides
    @RestApiScope
    public IHackerNewsItemsBatchProcessor provideHackerNewsItemsBatchProcessor(IHackerNewsItemsBatchProcessorCallbackHandler hackerNewsItemsBatchProcessorCallbackHandler,
                                                                               IHackerNewsItemRequestHandlerFactory hackerNewsItemRequestHandlerFactory)
    {
        return new HackerNewsItemsBatchProcessor(hackerNewsItemsBatchProcessorCallbackHandler, hackerNewsItemRequestHandlerFactory);
    }

}
