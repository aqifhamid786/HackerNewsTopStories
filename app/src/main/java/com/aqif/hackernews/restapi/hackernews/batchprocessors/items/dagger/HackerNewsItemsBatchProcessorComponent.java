package com.aqif.hackernews.restapi.hackernews.batchprocessors.items.dagger;

import com.aqif.hackernews.restapi.dagger.RestApiModule;
import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.dagger.HackerNewsItemApiModule;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.IHackerNewsItemsBatchProcessor;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@RestApiScope
@Component(modules = {HackerNewsItemsBatchProcessorModule.class,
        HackerNewsItemApiModule.class,
        RestApiModule.class     // rest api module
})
public interface HackerNewsItemsBatchProcessorComponent
{

    IHackerNewsItemsBatchProcessorCallbackHandler provideHackerNewsItemsBatchProcessorCallbackHandler();
    IHackerNewsItemsBatchProcessor provideHackerNewsItemsBatchProcessor();
}
