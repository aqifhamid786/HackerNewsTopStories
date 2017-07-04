package com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by aqifhamid.
 */

public interface IHackerNewsTopStoriesCallbackHandler extends IHackerNewsTopStoriesCallbackObservable, Callback<int[]>
{
    void setTag(Object tag);
}
