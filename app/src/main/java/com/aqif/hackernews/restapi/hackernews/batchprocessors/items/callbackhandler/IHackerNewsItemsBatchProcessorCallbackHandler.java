package com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchResponse;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemsBatchProcessorCallbackHandler extends IHackerNewsItemsBatchProcessorCallbackObservable
{
    void notifyItemsBatchSuccess(HackerNewsItemsBatchResponse hackerNewsItemsBatchResponse);
    void notifyItemsBatchFailure(String message, Object tag);
}
