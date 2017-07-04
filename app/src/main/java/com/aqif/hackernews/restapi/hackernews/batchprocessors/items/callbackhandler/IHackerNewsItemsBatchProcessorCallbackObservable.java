package com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemsBatchProcessorCallbackObservable
{
    void registerHackerNewsItemsBatchProcessorCallbackObservable(IHackerNewsItemsBatchProcessorCallbackObserver hackerNewsItemsBatchProcessorCallbackObserver);
    void unregisterHackerNewsItemsBatchProcessorCallbackObservable(IHackerNewsItemsBatchProcessorCallbackObserver hackerNewsItemsBatchProcessorCallbackObserver);
    void clear();
}
