package com.aqif.hackernews.restapi.hackernews.manager;

import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsCallbackBatchProcessorObservable;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackObservable;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsApiManager
{
    void fetchTopStories();
    void fetchItemsInABatch(int[] itemIds, Object tag);

    void cancelAllRequests();
    void clear();

    IHackerNewsApiManagerTopStoriesCallbackObservable getHackerNewsApiManagerTopStoriesObservable();
    IHackerNewsApiManagerItemsCallbackBatchProcessorObservable getHackerNewsApiManagerItemsBatchProcessorObservable();

}
