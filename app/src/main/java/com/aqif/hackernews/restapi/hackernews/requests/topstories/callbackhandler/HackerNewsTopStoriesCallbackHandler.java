package com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.HackerNewsConstants;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by aqifhamid.
 */

public class HackerNewsTopStoriesCallbackHandler implements  IHackerNewsTopStoriesCallbackHandler
{

    private Object mTag;
    private List<IHackerNewsTopStoriesCallbackObserver> mHackerNewsTopStoriesCallbackObservers;

    public HackerNewsTopStoriesCallbackHandler(List<IHackerNewsTopStoriesCallbackObserver> hackerNewsTopStoriesCallbackObservers)
    {
        mHackerNewsTopStoriesCallbackObservers = hackerNewsTopStoriesCallbackObservers;
    }

    @Override
    public void registerHackerNewsTopStoriesCallbackObserver(IHackerNewsTopStoriesCallbackObserver hackerNewsTopStoriesCallbackObserver)
    {
        if(hackerNewsTopStoriesCallbackObserver!=null && !mHackerNewsTopStoriesCallbackObservers.contains(hackerNewsTopStoriesCallbackObserver))
        {
            mHackerNewsTopStoriesCallbackObservers.add(hackerNewsTopStoriesCallbackObserver);
        }
    }

    @Override
    public void unregisterHackerNewsTopStoriesCallbackObserver(IHackerNewsTopStoriesCallbackObserver hackerNewsTopStoriesCallbackObserver)
    {
        if(hackerNewsTopStoriesCallbackObserver!=null && mHackerNewsTopStoriesCallbackObservers.contains(hackerNewsTopStoriesCallbackObserver))
        {
            mHackerNewsTopStoriesCallbackObservers.remove(hackerNewsTopStoriesCallbackObserver);
        }
    }

    @Override
    public void onResponse(Call<int[]> call, Response<int[]> response)
    {
        if(response.body()!=null)
        {
            for(int lop = 0; lop< mHackerNewsTopStoriesCallbackObservers.size(); lop++)
            {
                mHackerNewsTopStoriesCallbackObservers.get(lop).onHackerNewsTopStoriesSuccess(response.body(), mTag);
            }
        }
        else
        {
            for(int lop = 0; lop< mHackerNewsTopStoriesCallbackObservers.size(); lop++)
            {
                mHackerNewsTopStoriesCallbackObservers.get(lop).onHackerNewsTopStoriesFailed(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage, mTag);
            }
        }
    }

    @Override
    public void onFailure(Call<int[]> call, Throwable t)
    {
        for(int lop = 0; lop< mHackerNewsTopStoriesCallbackObservers.size(); lop++)
        {
            mHackerNewsTopStoriesCallbackObservers.get(lop).onHackerNewsTopStoriesFailed(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage, mTag);
        }
    }

    @Override
    public void setTag(Object tag)
    {
        mTag = tag;
    }

    @Override
    public void clear()
    {
        mHackerNewsTopStoriesCallbackObservers.clear();
        mTag = null;
    }

}
