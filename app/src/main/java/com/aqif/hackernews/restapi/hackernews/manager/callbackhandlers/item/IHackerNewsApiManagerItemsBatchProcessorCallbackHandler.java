package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import java.util.ArrayList;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsApiManagerItemsBatchProcessorCallbackHandler extends IHackerNewsApiManagerItemsCallbackBatchProcessorObservable
{
    void notifyItemsBatchSuccess(ArrayList<ItemDao> items, Object tag);
    void notifyItemsBatchFailure(String message, Object tag);
}
