package com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchResponse;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemsBatchProcessorCallbackObserver
{
    void onItemsBatchSuccess(HackerNewsItemsBatchResponse hackerNewsItemsBatchResponse);
    void onItemsBatchFailure(String message, Object tag);
}
