package com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemCallbackHandler extends IHackerNewsItemCallbackObservable, Callback<ItemDao>
{
    void setTag(Object tag);
}
