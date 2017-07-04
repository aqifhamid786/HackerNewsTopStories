package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import java.util.ArrayList;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsApiManagerItemsCallbackBatchProcessorObservable
{
    void registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(
            IHackerNewsApiManagerItemsBatchProcessorCallbackObserver hackerNewsApiManagerItemsBatchProcessorCallbackObserver);
    void unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver(
            IHackerNewsApiManagerItemsBatchProcessorCallbackObserver hackerNewsApiManagerItemsBatchProcessorCallbackObserver);
    void clear();
}
