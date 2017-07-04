package com.aqif.hackernews.restapi.hackernews.batchprocessors.items;

import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackObservable;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemsBatchProcessor
{

    IHackerNewsItemsBatchProcessorCallbackObservable getHackerNewsItemsBatchProcessorCallbackObservable();
    void processItemsBatchRequest(HackerNewsItemsBatchRequest HackerNewsItemsBatchRequest);

    void cancelAllBatches();
    void clear();
}
