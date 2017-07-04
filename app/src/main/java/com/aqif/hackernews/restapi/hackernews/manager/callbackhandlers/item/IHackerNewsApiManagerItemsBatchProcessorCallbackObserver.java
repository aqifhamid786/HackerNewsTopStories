package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import java.util.ArrayList;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsApiManagerItemsBatchProcessorCallbackObserver
{
    void onItemsBatchSuccess(ArrayList<ItemDao> items, Object tag);
    void onItemsBatchFailure(String message, Object tag);
}
