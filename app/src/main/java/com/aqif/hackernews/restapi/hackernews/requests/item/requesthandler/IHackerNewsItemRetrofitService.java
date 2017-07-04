package com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsItemRetrofitService
{

    @GET("item/{itemid}.json")
    Call<ItemDao> getItem(
            @Path("itemid") int itemId
    );

}
