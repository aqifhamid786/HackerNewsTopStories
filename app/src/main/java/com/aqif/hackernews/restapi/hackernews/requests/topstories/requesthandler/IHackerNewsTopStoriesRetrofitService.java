package com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsTopStoriesRetrofitService
{
    @GET("topstories.json")
    Call<int[]> getHackerNewsTopStories();
}
